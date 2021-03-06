package me.codetalk.demo.main;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import me.codetalk.demo.elastic.CustomerRepository;
import me.codetalk.demo.pojo.Customer;

@SpringBootApplication
@EnableElasticsearchRepositories(
	basePackages = {
		"me.codetalk.demo.elastic",
	}
)
public class DemoApp implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoApp.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ElasticsearchTemplate esTemplate;
	
	@Override
	public void run(String... arg0) throws Exception {
//		esTemplate.putMapping(Customer.class);
		
		this.customerRepository.deleteAll();
		
		saveCustomers();
//		fetchAllCustomers();
//		fetchIndividualCustomers();
		
		fetchByNativeQuery();
	}
	
	private void saveCustomers() {
		this.customerRepository.save(new Customer("1", "Alice", "Smith", "Java Programming Language", "attr2 x1"));
		this.customerRepository.save(new Customer("2", "Bob", "Smith", "Java Concurrency Programming", "attr2 x2"));
	}
	
	private void fetchAllCustomers() {
		LOGGER.info("====================> Fetch all customers");
		
		for(Customer customer : this.customerRepository.findAll()) {
			LOGGER.info("====================> " + customer);
		}
	}
	
	private void fetchIndividualCustomers() {
		LOGGER.info("====================> Customer found with findByFirstName('Alice')");
		LOGGER.info("====================> " + this.customerRepository.findByFirstName("Alice"));
		
		LOGGER.info("====================> Customer found with findByFirstNameAndLastName('Alice', 'Smith')");
		LOGGER.info("====================> " + this.customerRepository.findByFirstNameAndLastName("Alice", "Smith"));
		
		LOGGER.info("====================> Customers found with findByLastName('Smith')");
		for (Customer customer : this.customerRepository.findByLastName("Smith")) {
			System.out.println(customer);
		}
	}
	
	private void fetchByNativeQuery() {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
			    .withQuery(QueryBuilders.queryStringQuery("alice"))
			    .withIndices("customer5")
			    .withTypes("customer5")
			    .build();
		
		List<Customer> customers = esTemplate.queryForList(searchQuery, Customer.class);
		for(Customer customer : customers) {
			LOGGER.info("====================> " + customer);
		}
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApp.class, "--debug").close();
	}

}
