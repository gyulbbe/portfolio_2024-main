package com.hg.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.hg.dto.MemberDto;

public interface MemberDao {
	
	public ArrayList<HashMap<String, Object>> memberList(HashMap<String, Object> params);

	public int totalMemberCnt(HashMap<String, Object> params);

	public int join(MemberDto mDto);
	
	public int checkId(String memberId);
	
	public HashMap<String, Object> getMemberById(MemberDto mDto);
	
	public String getMemberPwById(MemberDto mDto);
	
	public String makeCipherText(MemberDto mDto);
	
	public int delMember(HashMap<String,Object> params);
}