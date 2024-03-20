package com.hg.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao {

//	댓글 리스트
	public ArrayList<HashMap<String, Object>> commentList(HashMap<String, Object> params);
	
//	댓글 작성
	public int writeComment(HashMap<String, Object> params);
	
//	댓글 수정
	public int updateComment(HashMap<String, Object> params);
	
//	댓글 삭제
	public int deleteComment(HashMap<String, Object> params);
	
}
