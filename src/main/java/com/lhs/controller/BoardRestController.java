package com.lhs.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
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
	public HashMap<String, Object> write(
			@RequestParam HashMap<String, Object> params, 
			MultipartHttpServletRequest mReq) {
		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		
//		bService.write(params, mReq.getFiles("attFiles"));
		return null;
	}
	
	@RequestMapping("/board/update.do")// !!!!!!!!!!!! 비동기 응답 
	public HashMap<String, Object> update(@RequestParam HashMap<String,Object> params, 
			MultipartHttpServletRequest mReq) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		
		int result = bService.update(params, null);
		HashMap<String, Object> map = new HashMap<>();
		//jsp에서 result를 이용해서 페이지 이동을 하는 함수가 있길래 넣어줌
		map.put("result", result);
		//jsp에 보낼 params
		map.put("map", params);
		//jsp에서 성공 시 뜨게 할 메시지 전달
		map.put("msg", "수정 완료");
		return map;
	}

	@RequestMapping("/board/delete.do")
	public HashMap<String, Object> delete(@RequestParam HashMap<String, Object> params, HttpSession session) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		Object boardSeq = params.get("boardSeq");
		System.out.println("111111111111111111boardSeq: " + boardSeq);
		
		int result = bService.delete(params);
		HashMap<String, Object> map = new HashMap<>();
		if(result ==1) {
			map.put("success", true);
			map.put("message", "삭제 완료");
		}
		else {
			map.put("success", false);
			map.put("message", "삭제 실패");
		}
		map.put("nextPage", "/board/list");
		return map; // 비동기: map return 
	}

	@RequestMapping("/board/deleteAttFile.do")
	public HashMap<String, Object> deleteAttFile(@RequestParam HashMap<String, Object> params) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		return null;
	}
	
	@RequestMapping("/board/download.do")
	public byte[] downloadFile(@RequestParam int fileIdx,
			HttpServletResponse rep) {
		//1. 받아온 파람의 파일 pk로 파일 전체 정보 불러온다
		
		//2. 받아온 정보를 토대로 물리적으로 저장된 실제 파일을 읽어온다.
		
	}
}
