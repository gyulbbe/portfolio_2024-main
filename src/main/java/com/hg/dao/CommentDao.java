package com.hg.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.hg.dto.BoardDto;
import com.hg.dto.CommentDto;

@Repository
public interface CommentDao {

//	댓글 리스트
	public ArrayList<HashMap<String, Object>> commentList(BoardDto bDto);
	
//	댓글 작성
	public int writeComment(CommentDto dto);
	
//	댓글 삭제
	public int deleteComment(CommentDto dto);
	
//	댓글 내용
	public CommentDto readComment(CommentDto dto);
	
}
