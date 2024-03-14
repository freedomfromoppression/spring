package com.future.my.commons.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileController {
   private static String CURR_IMATE_PATH ="c:\\tools\\spring_dev\\img";
   
   @RequestMapping("/download")
   public void download(@RequestParam("imageFileName")String imageFileName
         , HttpServletResponse resp) throws IOException {
	  System.out.println(imageFileName);
      OutputStream out = resp.getOutputStream();
      String downFile = CURR_IMATE_PATH + "\\" + imageFileName;
      File file = new File(downFile);
      // 해당경로 파일 없을경우
      if(!file.exists()) {
         resp.sendError(HttpServletResponse.SC_NOT_FOUND, "file not found");
      }
      resp.setHeader("Cache-Control", "no-cache");
      resp.setHeader("Content dispostion", "attachment fileName=" + imageFileName);
      try(FileInputStream in = new FileInputStream(file)){
         byte[] buffer = new byte[1024 * 8];
         while(true) {
            int count = in.read(buffer);
            if(count == -1) {
               break;
            }
            out.write(buffer, 0, count);
         }
      }catch (IOException ex) {
         ex.printStackTrace();
      }finally {
         out.close();
      }
   }
   @RequestMapping("/multiImgUpload")
   public void multiImgUpload(HttpServletRequest req, HttpServletResponse resp) {
   try {
	   String sFileInfo ="";
	   String fileName =req.getHeader("file-name");
	   String prifix = fileName.substring(fileName.lastIndexOf(".") + 1) ;
	   prifix = prifix.toLowerCase();
	   String path = CURR_IMATE_PATH;
	   File file = new File(path);
	   //저장 폴더 없을경우 생성
	   if(!file.exists()) {
		   file.mkdir();
	   }
	   //저장될 파일 이름
	   String realName = UUID.randomUUID().toString()+ "." + prifix;
	   InputStream is = req.getInputStream();
	   OutputStream os = new FileOutputStream(new File(path + "\\" + realName));
	   int read = 0;
	   byte buffer[] = new byte[1024];
	   while((read = is.read(buffer)) != -1) {
		   os.write(buffer, 0, read);
	   }
	   if(is != null) {
		   is.close();
	   }
	   os.flush();
	   os.close();
	   sFileInfo += "&bNewList=true";
	   sFileInfo += "&sFileName=" + fileName;
	   sFileInfo += "&sFileURL=/my/download?imageFileName=" + realName;
	   PrintWriter print = resp.getWriter();
	   print.print(sFileInfo);
	   print.flush();
	   print.close();
   }catch (IOException e) {
	 e.printStackTrace();
   }
  
}
}
