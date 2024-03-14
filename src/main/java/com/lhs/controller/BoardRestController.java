package com.lhs.controller;


import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lhs.service.AttFileService;
import com.lhs.service.BoardService;
import com.lhs.util.FileUtil;


@RestController
public class BoardRestController {

	@Autowired BoardService bService;
	@Autowired AttFileService attFileService;
	@Autowired FileUtil fileUtil;

	private String typeSeq = "2";

	@RequestMapping("/board/write.do")
	public HashMap<String, Object> write(@RequestParam HashMap<String, Object> params) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();

		int result = bService.write(params);
		//jsp창에서 result값이 1인 경우 정상 작동 아니면 index로 가게하는 코드 있음
		map.put("result", result);
		map.put("msg", "작성 완료");
		return map;
	}



	@RequestMapping("/board/update.do")// !!!!!!!!!!!! 비동기 응답 
	public HashMap<String, Object> update(@RequestParam HashMap<String,Object> params, HttpSession session, MultipartHttpServletRequest mReq) {
		HashMap<String, Object> map = new HashMap<>();
		//세션아이디가 없으면 로그인 화면으로
		String memberId = (String) session.getAttribute("memberId");
		if(Objects.isNull(memberId)||memberId.isEmpty()) {
			map.put("nextPage", "/member/login");
			return map;
		}

		//게시판 아이디
		String articleMemberId = (String) params.get("memberId");
		//세션 아이디와 로그인 아이디가 일치한다면
		if(memberId.equals(articleMemberId)) {

			if(!params.containsKey("typeSeq")) {
				params.put("typeSeq", this.typeSeq);
			}

			int result = bService.update(params, null);
			//jsp에서 result를 이용해서 페이지 이동을 하는 함수가 있길래 넣어줌
			if(result==1) {
				map.put("result", result);
				//jsp에 보낼 params
				map.put("map", params);
				//jsp에서 성공 시 뜨게 할 메시지 전달
				map.put("msg", "수정 완료");
			}
		} else {

		}
		return map;
	}

	@RequestMapping("/board/delete.do")
	public HashMap<String, Object> delete(@RequestParam HashMap<String, Object> params, HttpSession session) {

		HashMap<String, Object> map = new HashMap<>();
		//세션아이디가 없으면 로그인 화면으로
		String memberId = (String) session.getAttribute("memberId");
		if(Objects.isNull(memberId)||memberId.isEmpty()) {
			map.put("nextPage", "/member/login");
			return map;
		}
		//게시판 아이디
		String articleMemberId = (String) params.get("memberId");
		//세션 아이디와 로그인 아이디가 일치한다면
		if(memberId.equals(articleMemberId)) {
			if(!params.containsKey("typeSeq")) {
				params.put("typeSeq", this.typeSeq);
			}
			int result = bService.delete(params);
			if(result ==1) {
				map.put("success", true);
				map.put("message", "삭제 완료");
			}
			else {
				map.put("success", false);
				map.put("message", "삭제 실패");
			}
			map.put("nextPage", "/board/list");
		}
		//세션 아이디와 로그인 아이디가 일치하지 않는다면
		else {
			map.put("success", false);
			map.put("message", "본인 계정의 게시물이 아닙니다.");
			map.put("nextPage", "/board/list");
		}
		return map; // 비동기: map return 
	}

	@RequestMapping("/board/deleteAttFile.do")
	public HashMap<String, Object> deleteAttFile(@RequestParam HashMap<String, Object> params) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		return null;
	}

	//	@RequestMapping("/board/download.do")
	//	public byte[] downloadFile(@RequestParam int fileIdx,
	//			HttpServletResponse rep) {
	//		//1. 받아온 파람의 파일 pk로 파일 전체 정보 불러온다
	//		
	//		//2. 받아온 정보를 토대로 물리적으로 저장된 실제 파일을 읽어온다.
	//		
	//	}
}