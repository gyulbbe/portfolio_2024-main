package com.hg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
	//닉네임 검색 목록 받아오기
	public ArrayList<HashMap<String, Object>> searchNick(String memberNick, int offset);
	//닉네임 검색 총 게시물
	public int getTotalArticleCntNick(String memberNick);
	//제목 검색 목록 받아오기
	public ArrayList<HashMap<String, Object>> searchTitle(String title, int offset);
	//제목 검색 총 게시물
	public int getTotalArticleCntTitle(String title);
	
	public ArrayList<HashMap<String, Object>> boardList(HashMap<String, Object> params);
	
	public ArrayList<HashMap<String, Object>> noticeList(HashMap<String, String> params);
	
	public int getTotalArticleCnt();
	
	public int writeWithFile(HashMap<String, Object> params, List<MultipartFile> mFiles);
	
	public int write(HashMap<String, Object> params);

	/**
	 * 글 조회  
	 */
	public HashMap<String, Object> read(HashMap<String, Object> params);
	/**
	 * 글 수정 update 
	 * @param params
	 * @return
	 */
	public int update(HashMap<String, Object> params, List<MultipartFile> mFiles);
	
	/**첨부파일 삭제(수정 페이지에서 삭제버튼 눌러 삭제하는 경우임) 
	 * 
	 * @param params
	 * @return
	 */
	public boolean deleteAttFile(HashMap<String, Object> params);
	
	/** 글 삭제 delete 
	 * @param params
	 * @return
	 */
	public int delete(HashMap<String, Object> params);

}
