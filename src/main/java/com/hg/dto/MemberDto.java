package com.hg.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Alias("MemberDto")
public class MemberDto {
	
	int memberIdx;
	int memberTypeSeq;
	String memberId;
	String memberPw;
	String pwAgain;
	String memberName;
	String memberNick;
	String email;
	String createDtm;
	String updateDtm;
	String membercol;
}
