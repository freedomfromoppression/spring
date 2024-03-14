package com.future.my.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.my.board.dao.IBoardDAO;
import com.future.my.board.vo.BoardVO;
import com.future.my.board.vo.ReplyVO;

@Service
public class BoardService {
	@Autowired
	IBoardDAO dao;
	//게시글 조회
	public ArrayList<BoardVO> getBoardList(){
		return dao.getBoardList();
	}
	//게시글 등록
	public void writeBoard(BoardVO vo) throws Exception {
		int result = dao.writeBoard(vo);
		if(result == 0) {
			throw new Exception();
		}
	}
	public BoardVO getBoard(int boardNo) throws Exception {
		BoardVO result = dao.getBoard(boardNo);
		if(result == null) {
			throw new Exception();
		}
		return result;
	}
	//게시글 수정
	public void updateBoard(BoardVO vo) throws Exception  {
		int result = dao.updateBoard(vo);
		if(result == 0) {
			throw new Exception();
		}
	}
	//게시글 삭제
	public void deleteBoard(int BoardNo ) throws Exception   {
		int result = dao.deleteBoard(BoardNo);
		if(result == 0) {
			throw new Exception();
		}
	}
	//댓글 등록
	public void writeReply(ReplyVO vo) throws Exception  {
		int result = dao.writeReply(vo);
		if(result == 0) {
			throw new Exception();
		}
	}
	//댓글 조회
	public ReplyVO getReply(String replyNo) throws Exception {
		ReplyVO result = dao.getReply(replyNo);
		if(result == null) throw new Exception();
		return result;
	
}
	//댓글 리스트 조회
	public List<ReplyVO> getReplyList(int boardNo) throws Exception {
		List<ReplyVO> result = dao.getReplyList(boardNo);
		if(result == null) throw new Exception();
		return result;

	}   
}
