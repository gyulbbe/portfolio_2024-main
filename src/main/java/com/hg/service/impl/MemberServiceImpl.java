package com.hg.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import org.springframework.stereotype.Service;

import com.hg.dao.MemberDao;
import com.hg.dto.MemberDto;
import com.hg.exception.PasswordMissMatchException;
import com.hg.exception.UserNotFoundException;
import com.hg.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberDao mDao; 

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
	public int join(MemberDto mDto) {
		
		//비밀번호 길이가 6보다 작으면 안됨
		int pwLength = mDto.getMemberPw().length();
				
		if(pwLength<6) {
			return 0;
		}

		//비밀번호와 비밀번호 확인이 같아야 함
		String firstPw = mDto.getMemberPw();
		String againPw = mDto.getPwAgain();
		if(!Objects.equals(firstPw, againPw)) {
			return 0;
		}
		
		//비밀번호 암호화 후 저장
		firstPw = mDao.makeCipherText(mDto);
		mDto.setMemberPw(firstPw);
		
		//join이 제대로 실행되면 result는 1
		int result = 0;
		result = mDao.join(mDto);
		
		//result가 1이 아니라면 join자체가 실행이 제대로 안된 것
		if(result != 1) {
			return 0;
		}
		return result;
	}

	//아이디 중복 검사
	@Override
	public int checkId(String memberId) {
		return mDao.checkId(memberId);
	}

	//로그인
	@Override
	public HashMap<String, Object> login(MemberDto mDto) throws UserNotFoundException, PasswordMissMatchException {

		//db에 저장되어 있지 않으면 member에는 아무것도 들어가지 않음.
		//이거 편의를 위해서 sql문에서 dto대신 map형태로 반환
		HashMap<String, Object> member = mDao.getMemberById(mDto);

		//입력한 id가 db에 없을 때
		if(Objects.isNull(member)) {
			throw new UserNotFoundException();
		}

		//사용자가 입력한 pw 암호화 시킨 pw
		String encodePw = mDao.makeCipherText(mDto);

		//db에 저장되어 있는 pw(암호화가 되어있는 pw)
		String dbPw = mDao.getMemberPwById(mDto);

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
