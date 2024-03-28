package com.hg.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hg.dto.BoardDto;

@Repository
public interface BoardDao {
	
	//닉네임 검색
	public ArrayList<HashMap<String, Object>> searchNick(@Param("memberNick") String memberNick, @Param("offset") int offset);
	//닉네임 검색 총 게시물
	public int getTotalArticleCntNick(String memberNick);
	//제목 검색
	public ArrayList<HashMap<String, Object>> searchTitle(@Param("title") String title, @Param("offset") int offset);
	//제목 검색 총 게시물
	public int getTotalArticleCntTitle(String title);
	
	/**
	 * 모든 리스트 select  
	 * @param typeSeq
	 * @return
	 */
	
	public ArrayList<HashMap<String, Object>> boardList(HashMap<String, Object> params);
	
	/**
	 * 모든 공지 리스트 select  
	 * @param typeSeq
	 * @return
	 */
	 
	public ArrayList<HashMap<String, Object>> noticeList(HashMap<String, String> params);
	
	/**
	 * 총 글 수 
	 * @param params
	 * @return
	 */
	
	public int getTotalArticleCnt();
	
	/**
	 * 글 작성 insert 
	 * @param bDto
	 * @return
	 */
	public int write(BoardDto bDto);
	
	public int writeWithFile(HashMap<String, Object> params);
	
	/**
	 * 글 조회  
	 */
	public BoardDto read(BoardDto bDto);
	
	/**
	 * 조회수 증가.
	 * @param bDto
	 * @return
	 */
	public int updateHits(BoardDto bDto);
	
	/**
	 * 글 수정 update 
	 * @param bDto
	 * @return
	 */
	public int update(BoardDto bDto);
	
	/**
	 * 모든 첨부파일 삭제시 has_file = 0 으로 수정 
	 * @param params
	 * @return
	 */
	public int updateHasFileToZero(HashMap<String, Object> params);

	 
	/** 글 삭제 delete 
	 * @param params
	 * @return
	 */
	public int delete(BoardDto bDto);

	
	
}
