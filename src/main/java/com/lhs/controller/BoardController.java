package com.lhs.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lhs.service.AttFileService;
import com.lhs.service.BoardService;
import com.lhs.util.FileUtil;
import com.lhs.util.PageHandler;

@Controller
public class BoardController {

	@Autowired BoardService bService;
	@Autowired AttFileService attFileService;
	@Autowired FileUtil fileUtil;

	private String typeSeq = "2";

	//보드 리스트로
	@RequestMapping("/board/list.do")
	public HashMap<String, Object> goList(@RequestParam HashMap<String, Object> params,
			@RequestParam(value = "page", defaultValue = "1") int currentPage){

		PageHandler ph = new PageHandler();
		//총 게시물 수
		int total = bService.getTotalArticleCnt();
		//현재 페이지
		//currentPage

		//페이징
		ph.doPaging(currentPage, total);
		
		ArrayList<HashMap<String, Object>> boardList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		//db로 params에 offset까지 담아서 보내기 위한 작업
		int offset = ph.getOffset();
		params.put("offset", offset);
		
		//화면에 뿌려줄 데이터
		boardList = bService.boardList(params);
		
		//boardList라는 이름에 boardList데이터들 담기
		map.put("boardList", boardList);
		
		//현재 페이지
		currentPage = ph.getCurrentPage();
		map.put("currentPage", currentPage);
		
		//마지막(총) 페이지
		int totalPage = ph.getTotalPage();
		map.put("totalPage", totalPage);
		
		map.put("nextPage", "/board/list");
		return map;
	}

	@RequestMapping("/test.do")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test");
		return mv;
	}

	//글쓰기 페이지로 	
	@RequestMapping("/board/goToWrite.do")
	public ModelAndView goToWrite(@RequestParam HashMap<String, Object> params) {
		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/board/write");
		return mv;
	}

	@RequestMapping("/board/read.do")
	public ModelAndView read(@RequestParam HashMap<String, Object> params) {
		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		HashMap<String, Object> map = bService.read(params);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("read", map);
		mv.setViewName("/board/read");
		return mv;
	}	
	
	//수정 페이지로 	
	@RequestMapping("/board/goToUpdate.do")
	public ModelAndView goToUpdate(@RequestParam HashMap<String, Object> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		
		//수정페이지에서 전에 썼던 제목이나 본문 글 등을 불러오기 위한 작업
		HashMap<String, Object> map = bService.read(params);
		mv.addObject("boardInfo", map);
		
		mv.setViewName("/board/update");
		return mv;
	}
}