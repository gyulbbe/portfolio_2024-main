package com.hg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dao.CommentDao;
import com.hg.dto.CommentDto;
import com.hg.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired CommentDao cDao;


	//	댓글 리스트
	@Override
	public ArrayList<HashMap<String, Object>> commentList(HashMap<String, Object> params){
		return cDao.commentList(params);
	}

	//	댓글 작성
	@Override
	public int writeComment(CommentDto dto){
		return cDao.writeComment(dto);
	}


	//	댓글 삭제
	@Override
	public int deleteComment(CommentDto dto){
		return cDao.deleteComment(dto);
	}

	//	댓글 내용
	@Override
	public CommentDto readComment(CommentDto dto){
		return cDao.readComment(dto);
	}
}