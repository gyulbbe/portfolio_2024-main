package com.hg.rest.controller;

import java.util.HashMap;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hg.service.CommentService;

@RestController
public class CommentResstController {

	@Autowired
	CommentService cService;

	//댓글 작성
	@PostMapping("/comment/write.do")
	public HashMap<String, Object> writeComment(@RequestBody HashMap<String, Object> params) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		int result = cService.writeComment(params);

		if(result==1) {
			map.put("result", result);
			map.put("msg", "작성 완료");
		} else {
			map.put("result", result);
			map.put("msg", "작성 실패");
		}
		return map;
	}

	//댓글 수정
	@PutMapping("/comment/update.do")
	public HashMap<String, Object> updateComment(@RequestParam HashMap<String, Object> params, HttpSession session){

		HashMap<String, Object> map = new HashMap<String, Object>();

		String comment = cService.readComment(params);
		int result = cService.updateComment(params);

		if(result==1) {
			map.put("result", result);
			map.put("map", comment);
			map.put("msg", "수정 완료");
		} else {
			map.put("result", result);
			map.put("msg", "수정 실패");
		}
		return map;
	}

	@DeleteMapping("/comment/delete.do")
	public HashMap<String, Object> deleteComment(@RequestParam HashMap<String, Object> params, HttpSession session) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int result = cService.deleteComment(params);
		
		if(result==1) {
			map.put("result", result);
			map.put("msg", "삭제 완료");
		} else {
			map.put("reuslt", result);
			map.put("msg", "삭제 실패");
		}
		
		return map;
	}
}
