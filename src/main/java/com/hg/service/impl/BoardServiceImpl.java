package com.hg.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hg.dao.AttFileDao;
import com.hg.dao.BoardDao;
import com.hg.dto.BoardDto;
import com.hg.service.BoardService;
import com.hg.util.FileUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

	private final BoardDao bDao;
	private final AttFileDao attFileDao;
	private final FileUtil fileUtil;
	@Value("#{config['project.file.upload.location']}")
	private String saveLocation;
	
	//닉네임 검색
	@Override
	public ArrayList<HashMap<String, Object>> searchNick(String memberNick, int offset){
		return bDao.searchNick(memberNick, offset);
	}
	
	//닉네임 검색 총 게시물 수
	@Override
	public int getTotalArticleCntNick(String memberNick){
		return bDao.getTotalArticleCntNick(memberNick);
	}
	
	//제목 검색
	@Override
	public ArrayList<HashMap<String, Object>> searchTitle(String title, int offset){
		return bDao.searchTitle(title, offset);
	}
	
	//제목 검색 총 게시물 수
	@Override
	public int getTotalArticleCntTitle(String title){
		return bDao.getTotalArticleCntTitle(title);
	}
	
	//한 페이지에 보여줄 목록
	@Override
	public ArrayList<HashMap<String, Object>> boardList(HashMap<String, Object> params) {
		ArrayList<HashMap<String, Object>> boadList = bDao.boardList(params);
		return boadList;
	}
	
	@Override
	public ArrayList<HashMap<String, Object>> noticeList(HashMap<String, String> params) {
		ArrayList<HashMap<String, Object>> noticeList = bDao.noticeList(params);
		return noticeList;
	}

	@Override
	public int getTotalArticleCnt() {
		return bDao.getTotalArticleCnt();
	}

	//글만 작성
	@Override
	public int write(BoardDto bDto) {
		return bDao.write(bDto);
	}
	
	//파일있는 글 작성
	@Override
	public int writeWithFile(HashMap<String, Object> params, List<MultipartFile> mFiles) {	
		
		HashMap<String, Object> map = new HashMap<>();
		int result = bDao.writeWithFile(params);
		
		for(MultipartFile mFile:mFiles) {
			//to-do: smart.pdf --> (UUID).pdf
			//to-do: s,art
			String fakeName = UUID.randomUUID().toString().replaceAll("-", "");
			
			try {
				fileUtil.copyFile(mFile, fakeName);
				map.put("typeSeq", params.get("typeSeq"));
				map.put("boardSeq", params.get("boardSeq"));
				map.put("fileName", mFile.getOriginalFilename());
				map.put("fakeFileName", fakeName);
				map.put("fileSize", mFile.getSize());
				map.put("fileType", mFile.getContentType());
				map.put("saveLoc", saveLocation);
				int outcome = attFileDao.addAttFile(map);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
			System.out.println(mFile.getContentType());
			System.out.println(mFile.getName());
			System.out.println(mFile.getOriginalFilename());
			System.out.println(mFile.getSize());
			System.out.println("-----------------");
		}
		
//		System.out.println("params 결과: "+params);
		return result;
	}

	//글 조회 
	@Override
	public BoardDto read(BoardDto bDto) {
		//조회수 증가
		bDao.updateHits(bDto);
		return bDao.read(bDto);
	}

	@Override
	public int update(BoardDto bDto, List<MultipartFile> mFiles) {
//		if(params.get("hasFile").equals("Y")) { // 첨부파일 존재시 			
//			// 파일 처리
//		}	
//		// 글 수정 dao 
		return bDao.update(bDto);
	}

	@Override
	public int delete(BoardDto bDto) {
//		if(params.get("hasFile").equals("Y")) { // 첨부파일 있으면 		
//			 // 파일 처리
//		}
		return bDao.delete(bDto);
	}

	@Override
	public boolean deleteAttFile(HashMap<String, Object> params) {
		boolean result = false;		
		return result;
	}
}
