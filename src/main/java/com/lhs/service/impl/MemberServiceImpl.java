package com.lhs.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.dao.MemberDao;
import com.lhs.exception.PasswordMissMatchException;
import com.lhs.exception.UserNotFoundException;
import com.lhs.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired MemberDao mDao; 

	@Override
	public ArrayList<HashMap<String, Object>> memberList(HashMap<String, Object> params) {
		return mDao.memberList(params);
	}

	//총회원수
	@Override
	public int totalMemberCnt(HashMap<String, Object> params) {
		return mDao.totalMemberCnt(params);
	}

	//회원가입
	@Override
	public int join(HashMap<String, String> params) {

		//비밀번호 길이가 6보다 작으면 안됨
		int pwLength = params.get("memberPw").length();
		if(pwLength<6) {
			return 0;
		}

		//비밀번호와 비밀번호 확인이 같아야 함
		String firstPw = params.get("memberPw");
		String againPw = params.get("pwAgain");
		if(!Objects.equals(firstPw, againPw)) {
			return 0;
		}

		//비밀번호 암호화 후 저장
		firstPw = mDao.makeCipherText(params);
		params.put("memberPw",firstPw);

		//join이 제대로 실행되면 result는 1
		int result = mDao.join(params);

		//result가 1이라면 Dao의 join실행(회원가입)
		if(result == 1) {
			return result;
		}
		return 0;
	}

	//아이디 중복 검사
	@Override
	public int checkId(HashMap<String, String> params) {
		return mDao.checkId(params);
	}

	//로그인
	@Override
	public HashMap<String, Object> login(HashMap<String, String> params) throws UserNotFoundException, PasswordMissMatchException {

		//db에 저장되어 있지 않으면 member에는 아무것도 들어가지 않음.
		HashMap<String, Object> member = mDao.getMemberById(params);

		//입력한 id가 db에 없을 때
		if(Objects.isNull(member)) {
			throw new UserNotFoundException();
		}

		//사용자가 입력한 pw 암호화 시킨 pw
		String encodePw = mDao.makeCipherText(params);

		//db에 저장되어 있는 pw(암호화가 되어있는 pw)
		String dbPw = mDao.getMemberPwById(params);

		if(!Objects.equals(dbPw, encodePw)) {
			throw new PasswordMissMatchException();
		}
		
		return member;
	}

	//회원삭제
	@Override
	public int delMember(HashMap<String, Object> params) {	
		return mDao.delMember(params);
	}
}
