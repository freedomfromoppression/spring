package com.future.my.member.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.future.my.member.service.MemberService;
import com.future.my.member.vo.MemberVO;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/registView")
	public String registView() {
		return "member/registView";
	}
	
	@RequestMapping("/registDo")
	public String registDo(HttpServletRequest request
			, MemberVO member, RedirectAttributes re) {
		member.setMemPw(passwordEncoder.encode(request.getParameter("memPw")));
		System.out.println(member);
		try {
			memberService.registMember(member);
		} catch (Exception e) {
			e.printStackTrace();
			return "errorView";
		}
		//RedirectAttributes 리다이렉트시 전송하고 싶은데이터를 포함시켜서 리다이렉트 요청을 할 수 있음.
		re.addFlashAttribute("msg", "회원가입이 정상적으로 처리 되었습니다.");
		return "redirect:/";
	}
	
	@RequestMapping("/loginView")
	public String loginView(HttpServletRequest req, Model model) {
		// Model 뷰에 전달할 데이터를 담는 객체 (컨트롤러에서 뷰로 데이터 전달시 사용)
		// 현재 request요청이 어느 URL로 부터 왔는지(로그인 처리 후 이동시켜주려고) 
		String reqURL = req.getHeader("Referer");
		model.addAttribute("fromURL", reqURL);
		return "member/loginView";
	}	
	
	@RequestMapping("/loginDo")
	public String loginDo(MemberVO vo, HttpSession session
		,boolean remember ,String fromURL, RedirectAttributes re
		,HttpServletResponse resp) {
		MemberVO login = memberService.loginMember(vo);
		if(login == null) {
			re.addFlashAttribute("msg", "아이디를 확인해 주세요.");
			return "redirect:/loginView";
		}
		//첫번쨰 매개변수(사용자 입력 아직 암호화가 안된) 두번쨰 매개변수(암호화가 된 비밀번호)
		boolean match = passwordEncoder.matches(vo.getMemPw(), login.getMemPw());
		if(!match) {
			re.addFlashAttribute("msg", "비번을 확인해주세요.");
			return "redirect:/loginView";
		}
		
		
		
		
		
		
		session.setAttribute("login", login);
		//아이디 기억하기 선택했을때
		if(remember) {
			Cookie cookie = new Cookie("rememberId", vo.getMemId());
			resp.addCookie(cookie);// 응답하는 객체에 담아서 전달
		}else {
			Cookie cookie = new Cookie("rememberId","");
			cookie.setMaxAge(0); // 유효시간이 0
			resp.addCookie(cookie);
		}
		return "redirect:"+fromURL;
	}
	@RequestMapping("/logoutDo")
	public String logoutDo (HttpSession session, RedirectAttributes re) {
		//세션 종료		
		session.invalidate();
		re.addFlashAttribute("msg", "로그아웃 되셨습니다.");
		return "redirect:/";
	}
	
	@RequestMapping("/mypageView")
	public String mypageView(HttpSession session, Model model) {
		if(session.getAttribute("login") == null) {
			return "redirect:/loginView";
		}
		return "member/mypageView";
	}
	
	@PostMapping("/files/upload")
	public ResponseEntity<?> uploadFiles(
			@RequestParam("uploadImage") MultipartFile uploadImage
			,HttpSession session) throws Exception {
		//웹 접근경로
		String webPath = "/resources/mypage/";
		//서버 저장 폴더
		String folderPath = session.getServletContext().getRealPath(webPath);
		MemberVO login = (MemberVO) session.getAttribute("login");
		String imgPath=
				memberService.uploadProfile(login, folderPath, webPath, uploadImage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message","success");
		map.put("imgPath", imgPath); //화면에서 사용할 경로전달
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
}
