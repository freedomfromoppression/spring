package com.future.my.commons.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.future.my.commons.service.MailService;
import com.future.my.commons.vo.MailVO;

@Controller
@EnableAsync  //비동기 기능을 활성화(복잡한 스레드 관리 없이 비동기 쉽게 처리)
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping("/test")
	public String test() throws UnsupportedEncodingException {
		
		mailService.sendMail("gichan0514@gmail.com", "테스트야.....", "반갑다");
		
		return "home";
	}
	
	@RequestMapping("/sendMailDo")
	public String sendMailDo(MailVO mailVO
			, RedirectAttributes re) throws UnsupportedEncodingException {
		
		System.out.println(mailVO);
		List<String> arr = mailVO.getEmail();
		for(String email :arr) {
			mailService.sendMail(email, mailVO.getTitle(), mailVO.getContent());
		}
		re.addFlashAttribute("msg", "메일이 전송되었습니다.");
		return "redirect:/admin";
	}

}
