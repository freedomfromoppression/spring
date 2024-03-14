package com.future.my.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.my.chat.dao.IRoomDAO;
import com.future.my.chat.vo.ChatLogVO;
import com.future.my.chat.vo.RoomVO;

@Service
public class RoomService {
   @Autowired
   IRoomDAO dao;
   
   // 전체 채팅방 리스트
      public List<RoomVO> getRoomList(){
         return dao.getRoomList();
      }
      // 채팅방 정보조회
      public RoomVO getRoom(int roomNo) {
         return dao.getRoom(roomNo);
      }
      // 채팅방 생성
      public int createRoom(RoomVO vo) {
         return dao.createRoom(vo);
      }
      // 채팅내용
      public List<ChatLogVO> getChatList(int roomNo){
         return dao.getChatList(roomNo);
      }
      // 채팅내용기록
      public int insertchat(ChatLogVO chatVO) {
         return dao.insertChat(chatVO);
      }
}