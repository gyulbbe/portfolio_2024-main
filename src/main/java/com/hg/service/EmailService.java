package com.hg.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.hg.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(MemberDto mDto) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("gyulbbbe@gmail.com");
        message.setTo(mDto.getEmail()); 
        message.setSubject("회원가입 축하"); 
        message.setText("안녕하세요, " + mDto.getMemberName() + "님 제 포트폴리오에 오신 것을 환영합니다.");
        mailSender.send(message);
    }
}