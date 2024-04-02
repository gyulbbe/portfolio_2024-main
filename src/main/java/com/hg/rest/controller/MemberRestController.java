package com.hg.rest.controller;

import java.util.HashMap;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hg.dto.MemberDto;
import com.hg.service.EmailService;
import com.hg.service.MemberService;

import lombok.RequiredArgsConstructor;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberRestController {

	private final MemberService mService;
	private final EmailService eService;
	
	@Value("#{config['site.context.path']}")
	String ctx;
	
	@GetMapping("/member/checkId.do")
	public HashMap<String, Object> checkId(@RequestParam("memberId") String memberId){
		//cnt 초기화
		int cnt = 0;
		
		cnt = mService.checkId(memberId);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cnt", cnt);
		map.put("msg", cnt==1? "중복된 ID 입니다.":"");

		return map;
	}
	
	@PostMapping("/member/join.do")
	public HashMap<String, Object> join(@ModelAttribute MemberDto mDto){
		
		//반환전용 HashMap
		HashMap<String, Object> map = new HashMap<String, Object>();
				
		int cnt = 0;
		cnt = mService.join(mDto);
		map.put("cnt", cnt);
		map.put("msg", cnt==1 ? "회원 가입 완료!":"회원 가입 실패!");
		if(cnt== 1) {
			eService.sendEmail(mDto);
		}
		map.put("nextPage", cnt==1 ? "/member/goLoginPage.do" : "/member/goRegisterPage.do");
		return map;
	}
	
	@PostMapping("/member/login.do")
	public HashMap<String, Object> login(@ModelAttribute MemberDto mDto, HttpSession session){
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			//mService맵 형태로 보냄
			map.putAll(mService.login(mDto));
			
			//세션 생성
			session.setAttribute("memberId", map.get("member_id"));
			session.setAttribute("memberIdx", map.get("member_idx"));
			session.setAttribute("memberNick", map.get("member_nick"));
			
			map.put("nextPage", "/index.do");
		} catch (Exception e) {
			e.printStackTrace();
//			log.error("", e);
			map.put("nextPage", "/index.do");
			map.put("msg", e.getMessage());
		}
		return map;
	}
//	
//	@RequestMapping("/admin/memberList.do")//비동기식 호출
//	public HashMap<String, Object> memberList(@RequestParam HashMap<String, Object> params) {
//		// 페이징
//		// 모든 회원 가져오기
//		List<HashMap<String,Object>> memberList = new ArrayList<HashMap<String,Object>>();
//		//go to  JSP 
//
//		HashMap<String,Object> result = new HashMap<String,Object>();
//		//정해진  키 이름으로 넘겨주기.. 
//		result.put("page", params.get("page")); //현재 페이지 
//		result.put("rows", memberList); // 불러온 회원목록 
//		result.put("total", 1);// 전체 페이지 
//		result.put("records", 10); //전체 회원수 
//		return result;
//	}
//	
//	@RequestMapping("/member/delMember.do")
//	public HashMap<String,Object> delMember(@RequestParam HashMap<String, Object> params){
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		int result = 0;
//		map.put("msg", (result == 1) ? "삭제되었습니다.":"삭제 실패!");
//		map.put("result",result);
//		return map;
//	}
}