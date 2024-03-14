package com.future.my.board.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.future.my.board.service.BoardService;
import com.future.my.board.vo.BoardVO;
import com.future.my.board.vo.ReplyVO;
import com.future.my.commons.service.CodeService;
import com.future.my.commons.vo.CodeVO;
import com.future.my.member.vo.MemberVO;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;
	
	@Autowired
	CodeService codeService;
	
	//해당 컨드롤러에서 연결되는 모든 화면에서 comList를 모두 사용가능.
	@ModelAttribute("comList")
	public List<CodeVO> getCodeList(){
		return codeService.getCodeList(null);
	}
	
	@RequestMapping("/boardView")
	public String boardView(Model model) {
		ArrayList<BoardVO> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		return "board/boardView";
	}
	
	@RequestMapping("/boardWriteView")
	public String boardWriteView(HttpSession session) {
		if(session.getAttribute("login") == null) {
			return "redirect:/loginView";
		}
		return "board/boardWriteView";
	}
	@RequestMapping("/boardWriteDo")
	public String boardWriteDo(HttpSession session, BoardVO board) throws Exception {
		MemberVO vo = (MemberVO) session.getAttribute("login"); 
		board.setMemId(vo.getMemId());
		boardService.writeBoard(board);
		return "redirect:/boardView";
	}
	@RequestMapping("/boardDetailView")
	public String boardDetailView(int boardNo            
			, Model model) throws Exception {
		System.out.println(boardNo);
		BoardVO vo = boardService.getBoard(boardNo);
		List<ReplyVO> replyList = boardService.getReplyList(boardNo);	
		model.addAttribute("board", vo);
		model.addAttribute("replyList", replyList);
	
		return "board/boardDetailView";
	}
	@RequestMapping(value="/boardEditView", method=RequestMethod.POST)
	public String boardEditView(int boardNo
		   ,Model model) throws Exception {
		System.out.println(boardNo);
		BoardVO vo = boardService.getBoard(boardNo);
	model.addAttribute("board", vo);
					       
	    return "board/boardEditView";
	}	
	
	@RequestMapping("/boardEditDo")
	public String boardEditDo(BoardVO vo, Model model) throws Exception  {
		boardService.updateBoard(vo);
		return "redirect:/boardView";
	}	
	
	@RequestMapping(value="/boardDel", method=RequestMethod.POST)
	public String boardDel(int boardNo) throws Exception  {
		boardService.deleteBoard(boardNo);
		return "redirect:/boardView";
	}
	//@ResponseBody 객체를 데이터 형태로 리턴
	@ResponseBody
	@PostMapping("/writeReplyDo")  	//@ResponseBody json 데이터 형태로 요청을 받음
	public ReplyVO writeReplyDo(@RequestBody ReplyVO vo) throws Exception {
		System.out.println(vo);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		String uniqu = sdf.format(date);
		vo.setReplyNo(uniqu);
		boardService.writeReply(vo);
		ReplyVO result = boardService.getReply(uniqu);
		return result;
	}
}

