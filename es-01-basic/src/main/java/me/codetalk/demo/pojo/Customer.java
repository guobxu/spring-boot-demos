package me.codetalk.demo.pojo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(indexName = "customer1", type="customer1", shards=1, replicas=0, refreshInterval = "-1")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String customerId;
	private String firstName;
	private String lastName;
	
	public Customer() {}
	
	public Customer(String customerId, String firstName, String lastName) {
		this.setCustomerId(customerId);
		this.firstName = firstName;
		this.lastName = lastName;
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
	
}
