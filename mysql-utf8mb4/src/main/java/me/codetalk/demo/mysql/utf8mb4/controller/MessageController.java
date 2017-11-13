package me.codetalk.demo.mysql.utf8mb4.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.codetalk.demo.mysql.utf8mb4.mapper.MessageMapper;
import me.codetalk.demo.mysql.utf8mb4.pojo.Message;

@RestController
public class MessageController {

	@Autowired
	private MessageMapper mesgMapper;
	
	
	@RequestMapping(value = "/mesg/add", method = RequestMethod.POST)
	public List<Message> add(@RequestBody Map<String, Object> data, HttpServletRequest request) {
		Message newMesg = new Message();
		newMesg.setText(data.get("mesg_text").toString());
		mesgMapper.insertMesg(newMesg);
		
		return mesgMapper.selectLatest5();
	}
	
}
