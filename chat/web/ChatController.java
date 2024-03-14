package com.future.my.chat.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.future.my.chat.service.RoomService;
import com.future.my.chat.vo.ChatLogVO;
import com.future.my.chat.vo.RoomVO;

@Controller
public class ChatController {
   
   @Autowired
      RoomService roomService;

   
   @RequestMapping("/chatView")
   public String chatView(Model model, int roomNo) {
      RoomVO roomVO = roomService.getRoom(roomNo);
      List<ChatLogVO> chatList = roomService.getChatList(roomNo);
      model.addAttribute("room", roomVO);
      model.addAttribute("chatList", chatList);
      return "chat/chatView";
   }
   
   // 채팅 메세지 전달
   @MessageMapping("/hello/{roomNo}") // 클라이언트로부터 메세지 수
   @SendTo("/subscribe/chat/{roomNo}") // 수신한 내용을 클라이언트에게 전달
   public ChatLogVO broadcasting(ChatLogVO chatVO) {
      roomService.insertchat(chatVO);
      
      SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/ddd HH:mm");
      chatVO.setSendDate(sdf.format(new Date()));
      return chatVO;
      
   }
}
