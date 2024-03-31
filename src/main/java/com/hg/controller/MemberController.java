package com.hg.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	
	@GetMapping("/member/goLoginPage.do")
	public String goLogin() {
		return "member/login";
	}

	@GetMapping("/member/goRegisterPage.do")
	public String goRegisterPage() {
		return "member/register";
	}

	@GetMapping("/member/logout.do")
	public ModelAndView logout(HttpSession session){
		session.invalidate();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/index.do");
		return mv;
	}
	

}