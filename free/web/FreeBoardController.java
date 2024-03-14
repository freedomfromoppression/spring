package com.future.my.free.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.future.my.commons.service.CodeService;
import com.future.my.commons.vo.CodeVO;
import com.future.my.free.service.FreeService;
import com.future.my.free.vo.FreeBoardVO;
import com.future.my.free.vo.FreeSearchVO;


	
@Controller
public class FreeBoardController {
	@Autowired
	FreeService freeService;
	
	@Autowired
	CodeService codeService;
	
	@ModelAttribute("comList")
	public List<CodeVO> getCodeList(){
		return codeService.getCodeList("BC00");
	}
	//게시글 리스트
	@RequestMapping("/freeList")
	public String freeList(Model model
			,@ModelAttribute("searchVO") FreeSearchVO searchVO) {
		
		List<FreeBoardVO> freeList = freeService.getBoardList(searchVO);
		model.addAttribute("freeList", freeList);
		return "free/freeList";
	}
		@RequestMapping("/freeView")
		public String freeView(Model model, int boNo) {
		   try {
		   FreeBoardVO freeBoard = freeService.getBoard(boNo);
		   model.addAttribute("freeBoard", freeBoard);
       } catch (Exception e) {
    	   e.printStackTrace();
    	   return "redirect:/freeList";
       }
		   return "free/freeView";
	
	}
		@RequestMapping("/freeForm")
		public String freeForm() {
			return "free/freeForm";
		}
		
		@RequestMapping("/freeBoardWriteDo")
		public String freeBoardWriteDo(FreeBoardVO freeBoardVO) throws Exception {
			freeService.insertFreeBoard(freeBoardVO);
			return "redirect:/freeList";
		}
		
}
