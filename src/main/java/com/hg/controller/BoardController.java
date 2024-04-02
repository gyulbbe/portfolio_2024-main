package com.hg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hg.dto.BoardDto;
import com.hg.service.AttFileService;
import com.hg.service.BoardService;
import com.hg.service.CommentService;
import com.hg.util.FileUtil;
import com.hg.util.PageHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService bService;
	private final CommentService cService;
//	private final AttFileService attFileService;
//	private final FileUtil fileUtil;
	@Value("#{config['site.context.path']}")
	String ctx;
	
	//보드 리스트로 + 검색
	@GetMapping("/board/list.do")
	public ModelAndView goList(@RequestParam HashMap<String, Object> params, //그냥 리스트
			@RequestParam(value = "searchType", required = false) String searchType, //닉네임 검색인지 제목 검색인지
			@RequestParam(value = "keyword", required = false) String keyword, //검색창에 입력한 값
			@RequestParam(value = "page", defaultValue = "1") int currentPage){

		PageHandler ph = new PageHandler();
		ArrayList<HashMap<String, Object>> boardList = new ArrayList<HashMap<String, Object>>();
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
		
		//검색 후 페이지 이동을 위한 값 전달
		mv.addObject("searchType", searchType);
		mv.addObject("keyword", keyword);
		
		//다음 페이지
		mv.setViewName("/board/list");

		return mv;
	}


	@GetMapping("/test.do")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test");
		return mv;
	}

	//글쓰기 페이지로 	
	@GetMapping("/board/goToWrite.do")
	public ModelAndView goToWrite(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		//세션아이디가 없으면 로그인 화면으로
		String memberId = (String) session.getAttribute("memberId");
		if(Objects.isNull(memberId)||memberId.isEmpty()) {
			mv.setViewName("/member/login");
		}
		//세션아이디가 있으면 글쓰기 페이지로
		else {
			mv.setViewName("/board/write");
		}
		return mv;
	}

	//게시글 읽기
	@GetMapping("/board/read.do")
	public ModelAndView read(@ModelAttribute BoardDto bDto, HttpSession session, 
			@RequestParam(value = "currentPage", required = false) Integer currentPage, 
			@RequestParam(value = "searchType", required = false) String searchType, //닉네임 검색인지 제목 검색인지
			@RequestParam(value = "keyword", required = false) String keyword //검색창에 입력한 값
			) {
		
		ModelAndView mv = new ModelAndView();
		//전에 썼던 내용
		BoardDto boardInfo = bService.read(bDto);
		//댓글 리스트
		ArrayList<HashMap<String, Object>> commentList = cService.commentList(bDto);
		
		//페이지 변경 후 게시글을 읽었을 때, 전의 페이지로 가기 위한 작업
		mv.addObject("currentPage", currentPage);
		
		//게시글 검색 후 페이지 변경 후 게시글 읽었을 때, 전의 페이지로 가기 위한 작업
		mv.addObject("searchType", searchType);
		mv.addObject("keyword", keyword);
		
		mv.addObject("commentList", commentList);
		mv.addObject("read", boardInfo);
		mv.setViewName("/board/read");
		return mv;
	}

	//수정 페이지로 
	@GetMapping("/board/goToUpdate.do")
	public ModelAndView goToUpdate(@ModelAttribute BoardDto bDto, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		//세션 없으면 로그인 페이지로
		int memberIdx = (int) session.getAttribute("memberIdx");
		if(Objects.isNull(memberIdx)||memberIdx ==0 ) {
			mv.setViewName("/member/login");
		}
		
		//수정페이지에서 전에 썼던 제목이나 본문 글 등을 불러오기 위한 작업
		BoardDto boardInfo = bService.read(bDto);
		if(!Objects.isNull(boardInfo)) {
			mv.addObject("boardInfo", boardInfo);
			mv.setViewName("/board/update");
		}
		return mv;
	}
}
