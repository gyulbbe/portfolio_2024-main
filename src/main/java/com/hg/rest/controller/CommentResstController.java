package com.hg.rest.controller;

import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hg.dto.CommentDto;
import com.hg.service.CommentService;

@RestController
public class CommentResstController {

	@Autowired
	CommentService cService;

	//댓글 작성
	@PostMapping("/comment/write.do")
	public HashMap<String, Object> writeComment(@RequestBody CommentDto dto, HttpSession session) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		//세션아이디가 없으면 댓글 입력 막기
		String memberId = (String) session.getAttribute("memberId");
		if(Objects.isNull(memberId)||memberId.isEmpty()) {
			map.put("success", false);
			map.put("msg", "비로그인 상태입니다.");
			return map;
		}

		int result = cService.writeComment(dto);
		//날짜만 억지로 집어넣기 위해 따로 생성
		//날짜는 sql에서 생성하면 dto에 자동으로 담기게 설정해도 담기지가 않음
		//만약 자동으로 담기게 하고 싶으면 자바에서 생성해서 db에 집어넣어야 함
		CommentDto dto1 = cService.readComment(dto);

		System.out.println(dto.toString());
		if(result==1) {
			map.put("success", true);
			map.put("msg", "작성 완료");
			map.put("memberId", dto.getMemberId());
			map.put("memberNick", dto.getMemberNick());
			map.put("createDtm", dto1.getCreateDtm());
			map.put("commentContent", dto.getCommentContent());
			map.put("commentSeq", dto.getCommentSeq());
		} else {
			map.put("success", false);
			map.put("msg", "작성 실패");
		}
		return map;
	}

	@DeleteMapping("/comment/delete.do")
	public HashMap<String, Object> deleteComment(@RequestBody CommentDto dto, HttpSession session) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		int result = cService.deleteComment(dto);
		
		//세션아이디가 없으면 댓글 삭제 막기
		String memberId = (String) session.getAttribute("memberId");
		if(Objects.isNull(memberId)||memberId.isEmpty()) {
			map.put("result", 0);
			map.put("msg", "비로그인 상태입니다.");
			return map;
		}

		

		if(result==1) {
			map.put("result", result);
			map.put("msg", "삭제 완료");
		} else {
			map.put("result", result);
			map.put("msg", "삭제 실패");
		}
		
		return map;
	}
}
