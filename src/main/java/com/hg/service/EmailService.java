package com.hg.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(HashMap<String, String> params) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("gyulbbbe@gmail.com");
        message.setTo(params.get("email")); 
        message.setSubject("회원가입 축하"); 
        message.setText("안녕하세요, " + params.get("memberId") + "님 제 포트폴리오에 오신 것을 환영합니다.");
        mailSender.send(message);
    }
}