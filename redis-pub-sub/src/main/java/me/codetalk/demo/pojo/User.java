package me.codetalk.demo.pojo;

public class User {

	private String id;
	private String name;
	private Long birth;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getBirth() {
		return birth;
	}

	public void setBirth(Long birth) {
		this.birth = birth;
	}
	
	@Override
	public String toString() {
		return "User =  { id = " + getId() + 
				", name = " + getName() +
				", birth = " + getBirth() + 
				"}";
	}
	
	
}
