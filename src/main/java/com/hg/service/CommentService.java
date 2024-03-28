package com.hg.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.hg.dto.BoardDto;
import com.hg.dto.CommentDto;

public interface CommentService {
//	댓글 리스트
	public ArrayList<HashMap<String, Object>> commentList(BoardDto bDto);
	
//	댓글 작성
	public int writeComment(CommentDto dto);

//	댓글 삭제
	public int deleteComment(CommentDto dto);

//	댓글 내용+날짜
	public CommentDto readComment(CommentDto dto);
}
