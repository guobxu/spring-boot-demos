package me.codetalk.demo.springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	@JsonProperty("user_id")
	private Long id;
	
	@JsonProperty("user_name")
	private String name;
	
	@JsonProperty("user_profile")
	private String profile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	
}
