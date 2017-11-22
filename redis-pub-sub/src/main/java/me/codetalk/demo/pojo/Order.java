package me.codetalk.demo.pojo;

public class Order {

	private String id;
	private String no;
	private Long createDate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNo() {
		return no;
	}
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public Long getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "Order =  { id = " + getId() + 
				", no = " + getNo() +
				", createDate = " + getCreateDate() + 
				"}";
	}
	
}
