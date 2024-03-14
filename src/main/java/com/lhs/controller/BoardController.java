package com.lhs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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

	//보드 리스트로 + 검색
	@RequestMapping("/board/list.do")
	public ModelAndView goList(@RequestParam HashMap<String, Object> params, //그냥 리스트
			@RequestParam(value = "searchType", required = false) String searchType, //닉네임 검색인지 제목 검색인지
			@RequestParam(value = "keyword", required = false) String keyword, //검색창에 입력한 값
			@RequestParam(value = "page", defaultValue = "1") int currentPage){

		PageHandler ph = new PageHandler();
		ArrayList<HashMap<String, Object>> boardList = new ArrayList<HashMap<String, Object>>();
		//			HashMap<String, Object> map = new HashMap<String, Object>();
		ModelAndView mv = new ModelAndView();

		//닉네임 검색이면
		if("memberNick".equals(searchType)){
			//총 게시물 수
			int totalArticleCntNick = bService.getTotalArticleCntNick(keyword);
			//페이징
			ph.doPaging(currentPage, totalArticleCntNick);
			//db에 보낼 offset저장
			int offset = ph.getOffset();
			//화면에 뿌려줄 데이터
			boardList = bService.searchNick(keyword, offset);
		}

		//제목 검색이면
		else if("title".equals(searchType)){
			//총 게시물 수
			int totalArticleCntTitle = bService.getTotalArticleCntTitle(keyword);
			//페이징
			ph.doPaging(currentPage, totalArticleCntTitle);
			//db에 보낼 offset저장
			int offset = ph.getOffset();
			//화면에 뿌려줄 데이터
			boardList = bService.searchTitle(keyword, offset);
		}

		//그냥 맨 처음 list들어갔을 때
		else {
			//총 게시물 수
			int total = bService.getTotalArticleCnt();

			//페이징
			ph.doPaging(currentPage, total);

			//db로 params에 offset까지 담아서 보내기 위한 작업
			int offset = ph.getOffset();
			params.put("offset", offset);

			//화면에 뿌려줄 데이터
			boardList = bService.boardList(params);
		}

		//boardList라는 이름에 boardList데이터들 담기
		mv.addObject("boardList", boardList);

		//현재 페이지
		currentPage = ph.getCurrentPage();
		mv.addObject("currentPage", currentPage);

		//마지막(총) 페이지
		int totalPage = ph.getTotalPage();
		mv.addObject("totalPage", totalPage);

		//현재 줄 첫 페이지
		int beginPage = ph.getBeginPage();
		mv.addObject("beginPage", beginPage);

		//현재 줄 마지막 페이지
		int endPage = ph.getEndPage();
		mv.addObject("endPage", endPage);

		//다음 페이지
		mv.setViewName("/board/list");

		return mv;
	}



	//	//검색
	//	@GetMapping("/board/search.do")
	//	public HashMap<String, Object> search(@RequestParam String searchType, 
	//			@RequestParam String keyword,
	//			@RequestParam(value = "page", defaultValue = "1") int currentPage){
	//		ArrayList<HashMap<String, Object>> boardList = new ArrayList<HashMap<String, Object>>();
	//		HashMap<String, Object> map = new HashMap<String, Object>();
	//		PageHandler ph = new PageHandler();
	//
	//		//닉네임 검색이면
	//		if("memberNick".equals(searchType)){
	//			//총 게시물 수
	//			int totalArticleCntNick = bService.getTotalArticleCntNick(keyword);
	//			//페이징
	//			ph.doPaging(currentPage, totalArticleCntNick);
	//			//db에 보낼 offset저장
	//			int offset = ph.getOffset();
	//
	//			boardList = bService.searchNick(keyword, offset);
	//			//boardList라는 이름에 닉네임으로 분류된 데이터들 담기
	//			map.put("boardList", boardList);
	//
	//			//현재 페이지
	//			currentPage = ph.getCurrentPage();
	//			map.put("currentPage", currentPage);
	//
	//			//마지막(총) 페이지
	//			int totalPage = ph.getTotalPage();
	//			map.put("totalPage", totalPage);
	//
	//			//현재 줄 첫 페이지
	//			int beginPage = ph.getBeginPage();
	//			map.put("beginPage", beginPage);
	//
	//			//현재 줄 마지막 페이지
	//			int endPage = ph.getEndPage();
	//			map.put("endPage", endPage);
	//
	//		}
	//
	//		//제목 검색이면
	//		else if("title".equals(searchType)){
	//			//총 게시물 수
	//			int totalArticleCntTitle = bService.getTotalArticleCntTitle(keyword);
	//			//페이징
	//			ph.doPaging(currentPage, totalArticleCntTitle);
	//			//db에 보낼 offset저장
	//			int offset = ph.getOffset();
	//
	//			boardList = bService.searchTitle(keyword, offset);
	//			//boardList라는 이름에 닉네임으로 분류된 데이터들 담기
	//			map.put("boardList", boardList);
	//
	//			//현재 페이지
	//			currentPage = ph.getCurrentPage();
	//			map.put("currentPage", currentPage);
	//
	//			//마지막(총) 페이지
	//			int totalPage = ph.getTotalPage();
	//			map.put("totalPage", totalPage);
	//
	//			//현재 줄 첫 페이지
	//			int beginPage = ph.getBeginPage();
	//			map.put("beginPage", beginPage);
	//
	//			//현재 줄 마지막 페이지
	//			int endPage = ph.getEndPage();
	//			map.put("endPage", endPage);
	//
	//		}
	//		map.put("nextPage", "/board/list");
	//		return map;
	//	}

	@RequestMapping("/test.do")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test");
		return mv;
	}

	//글쓰기 페이지로 	
	@RequestMapping("/board/goToWrite.do")
	public ModelAndView goToWrite(@RequestParam HashMap<String, Object> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		//세션아이디가 없으면 로그인 화면으로
		String memberId = (String) session.getAttribute("memberId");
		if(Objects.isNull(memberId)||memberId.isEmpty()) {
			mv.setViewName("/member/login");
		}
		//세션아이디가 있으면 글쓰기 페이지로
		else {
			if(!params.containsKey("typeSeq")) {
				params.put("typeSeq", this.typeSeq);
			}
			mv.setViewName("/board/write");
		}
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
		//세션아이디가 없으면 로그인 화면으로
		String memberId = (String) session.getAttribute("memberId");
		if(Objects.isNull(memberId)||memberId.isEmpty()) {
			mv.setViewName("/member/login");
			return mv;
		}
		
		//게시판 아이디
		String articleMemberId = (String) params.get("memberId");
		//세션 아이디와 로그인 아이디가 일치한다면
		if(memberId.equals(articleMemberId)) {
			if(!params.containsKey("typeSeq")) {
				params.put("typeSeq", this.typeSeq);
			}
			//수정페이지에서 전에 썼던 제목이나 본문 글 등을 불러오기 위한 작업
			HashMap<String, Object> map = bService.read(params);
			mv.addObject("boardInfo", map);

			mv.setViewName("/board/update");
		} else {
			
		}
		return mv;
	}
}