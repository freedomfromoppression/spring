package com.future.my.commons.service;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender  mailSender;
	
	//비동기 메서드 (메일을 보낼떄 마다 시간이 다르기 떄문에 비동기로 처리
	@Async
	public void sendMail(String to, String title, String content) throws UnsupportedEncodingException {	
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper;
			try {
				messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom("gichan0514@gmail.com", "관리자");
				messageHelper.setSubject(title);
				messageHelper.setTo(to);
				messageHelper.setText(content);
				mailSender.send(message);    //전송
				
			} catch (javax.mail.MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}

}
