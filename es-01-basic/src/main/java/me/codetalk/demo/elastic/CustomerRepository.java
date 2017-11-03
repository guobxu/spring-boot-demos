package me.codetalk.demo.elastic;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import me.codetalk.demo.pojo.Customer;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {
	
	public Customer findByFirstName(String firstName);
	
	public List<Customer> findByLastName(String lastName);
	
	public Customer findByFirstNameAndLastName(String firstName, String lastName);

}
