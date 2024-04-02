package com.hg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.hg.dao.CommentDao;
import com.hg.dto.BoardDto;
import com.hg.dto.CommentDto;
import com.hg.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

	private final CommentDao cDao;


	//	댓글 리스트
	@Override
	public ArrayList<HashMap<String, Object>> commentList(BoardDto bDto){
		return cDao.commentList(bDto);
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