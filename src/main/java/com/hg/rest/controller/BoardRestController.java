package com.hg.rest.controller;


import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hg.dto.BoardDto;
import com.hg.service.AttFileService;
import com.hg.service.BoardService;
import com.hg.util.FileUtil;


@RestController
public class BoardRestController {

	@Autowired BoardService bService;
	@Autowired AttFileService attFileService;
	@Autowired FileUtil fileUtil;

	@Value("#{config['site.context.path']}")
	String ctx;
	
	@PostMapping("/board/write.do")
	public HashMap<String, Object> write(@RequestBody BoardDto bDto, HttpSession session) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//세션 끊긴 상태로 글 작성 막기
		String sessionId = (String) session.getAttribute("memberId");
		int boardSeq = bDto.getBoardSeq();
		if(Objects.isNull(sessionId)) {
			map.put("result", 0);
			map.put("msg", "로그아웃된 상태입니다.");
		} else {
			int result = bService.write(bDto);
			//jsp창에서 result값이 1인 경우 정상 작동 아니면 index로 가게하는 코드 있음
			map.put("result", result);
			//글이 작성한 페이지를 바로 가기 위한 값
			map.put("boardSeq", boardSeq);
			map.put("msg", "작성 완료");
		}
		return map;
	}



	@PostMapping("/board/update.do")// !!!!!!!!!!!! 비동기 응답 
	public HashMap<String, Object> update(@ModelAttribute BoardDto bDto, MultipartHttpServletRequest mReq, HttpSession session) {
		HashMap<String, Object> map = new HashMap<>();
		//세션 끊긴 상태로 글 작성 막기
		String sessionId = (String) session.getAttribute("memberId");
		if(Objects.isNull(sessionId)) {
			map.put("result", 0);
			map.put("msg", "로그아웃된 상태입니다.");
		} else {
			int result = bService.update(bDto, null);
			//jsp에서 result를 이용해서 페이지 이동을 하는 함수가 있길래 넣어줌
			if(result==1) {
				map.put("result", result);
				//jsp에 보낼 params 이거 왜 필요한거지?
//				map.put("map", );
				//jsp에서 성공 시 뜨게 할 메시지 전달
				map.put("msg", "수정 완료");
			} else {
				map.put("result", result);
				//jsp에서 성공 시 뜨게 할 메시지 전달
				map.put("msg", "수정 실패");
			}
		}
		return map;
	}
	
	@PostMapping("/board/delete.do")
	public HashMap<String, Object> delete(@ModelAttribute BoardDto bDto, HttpSession session) {

		HashMap<String, Object> map = new HashMap<>();
		//세션아이디가 없으면 로그인 화면으로
		String memberId = (String) session.getAttribute("memberId");
		if(Objects.isNull(memberId)||memberId.isEmpty()) {
			map.put("success", false);
			map.put("message", "비로그인 상태입니다.");
			map.put("nextPage", "/member/login");
			return map;
		}
		//게시판 아이디
		String articleMemberId = bDto.getMemberId();
		//세션 아이디와 게시물 아이디가 일치한다면
		if(memberId.equals(articleMemberId)) {
			
			int result = bService.delete(bDto);
			
			if(result ==1) {
				map.put("success", true);
				map.put("message", "삭제 완료");
			} else {
				map.put("success", false);
				map.put("message", "삭제 실패");
			}
			map.put("nextPage", "/board/list");
		}
		//세션 아이디와 게시물 아이디가 일치하지 않는다면
		else {
			map.put("success", false);
			map.put("message", "본인의 게시물이 아닙니다.");
			map.put("nextPage", "/board/list");
		}
		return map; // 비동기: map return 
	}

//	@PostMapping("/board/deleteAttFile.do")
//	public HashMap<String, Object> deleteAttFile(@RequestParam HashMap<String, Object> params) {
//		
//		return null;
//	}
	
	//	@RequestMapping("/board/download.do")
	//	public byte[] downloadFile(@RequestParam int fileIdx,
	//			HttpServletResponse rep) {
	//		//1. 받아온 파람의 파일 pk로 파일 전체 정보 불러온다
	//		
	//		//2. 받아온 정보를 토대로 물리적으로 저장된 실제 파일을 읽어온다.
	//		
	//	}
}