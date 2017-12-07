package me.codetalk.demo.pojo;

import java.sql.Timestamp;

import me.codetalk.pojo.Shardable;

public class User implements Shardable {

	private Long id;
	private String login;
	private Timestamp createDate;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	public String toString() {
		return "[id = " + id + ", login = " + login + "]";
	}
	
}
