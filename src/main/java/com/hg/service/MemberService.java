package com.hg.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.hg.dto.MemberDto;
import com.hg.exception.PasswordMissMatchException;
import com.hg.exception.UserNotFoundException;

public interface MemberService {

	public ArrayList<HashMap<String, Object>> memberList(HashMap<String, Object> params);
	
	//총 회원수 for paging
	public int totalMemberCnt(HashMap<String, Object> params);

	public int join(MemberDto mDto);
	
	public int checkId(String memberId);
	
	public HashMap<String, Object> login(MemberDto mDto) throws UserNotFoundException, PasswordMissMatchException;

	public int delMember(HashMap<String,Object> params);
}