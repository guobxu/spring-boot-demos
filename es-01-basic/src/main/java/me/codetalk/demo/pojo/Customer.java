package me.codetalk.demo.pojo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(indexName = "customer7", type="customer7", shards=1, replicas=0, refreshInterval = "-1")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String customerId;
	private String firstName;
	private String lastName;
	
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String attr1;
	
	@JsonIgnore
	private String attr2;
	
	public Customer() {}
	
	public Customer(String customerId, String firstName, String lastName, String attr1, String attr2) {
		this.setCustomerId(customerId);
		this.firstName = firstName;
		this.lastName = lastName;
		this.attr1 = attr1;
		this.attr2 = attr2;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[customerId=%s, firstName='%s', lastName='%s']", this.customerId,
				this.firstName, this.lastName);
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	
}
