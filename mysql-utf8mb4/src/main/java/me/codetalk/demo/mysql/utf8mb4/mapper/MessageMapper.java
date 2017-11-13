package me.codetalk.demo.mysql.utf8mb4.mapper;

import java.util.List;

import me.codetalk.demo.mysql.utf8mb4.pojo.Message;

public interface MessageMapper {

	public void insertMesg(Message mesg);
	
	public List<Message> selectLatest5();
	
}
