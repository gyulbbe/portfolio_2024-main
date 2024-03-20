package com.hg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dao.CommentDao;
import com.hg.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired CommentDao cDao;
	

//	댓글 읽기
	@Override
	public ArrayList<HashMap<String, Object>> commentList(HashMap<String, Object> params){
		return cDao.commentList(params);
	}
	
//	댓글 작성
	@Override
	public int writeComment(HashMap<String, Object> params){
		return cDao.writeComment(params);
	}
	
//	댓글 수정
	@Override
	public int updateComment(HashMap<String, Object> params){
		return cDao.updateComment(params);
	}
	
//	댓글 삭제
	@Override
	public int deleteComment(HashMap<String, Object> params){
		return cDao.deleteComment(params);
	}
}
