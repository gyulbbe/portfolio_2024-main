package com.lhs.controller;

import java.util.HashMap;

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

		return null;
	}
	
	@RequestMapping("/board/update.do")// !!!!!!!!!!!! 비동기 응답 
	public HashMap<String, Object> update(@RequestParam HashMap<String,Object> params, 
			MultipartHttpServletRequest mReq) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}

		return null;
	}

	@RequestMapping("/board/delete.do")
	public HashMap<String, Object> delete(@RequestParam HashMap<String, Object> params, HttpSession session) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		return null; // 비동기: map return 
	}

	@RequestMapping("/board/deleteAttFile.do")
	public HashMap<String, Object> deleteAttFile(@RequestParam HashMap<String, Object> params) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		return null;
	} 
}
