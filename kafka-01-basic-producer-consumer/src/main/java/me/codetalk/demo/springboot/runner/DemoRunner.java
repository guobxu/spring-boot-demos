package me.codetalk.demo.springboot.runner;

import java.util.Random;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.codetalk.demo.springboot.entity.User;
import me.codetalk.demo.springboot.kafka.Message;
import me.codetalk.demo.springboot.kafka.MessageProducer;
import me.codetalk.demo.util.JsonUtils;

@Component
public class DemoRunner implements CommandLineRunner {

	@Autowired
	private MessageProducer mesgProducer;

	private Random rand = new Random();
	
	@Override
	public void run(String... arg0) throws Exception {
		Thread.sleep(2000);
		
		Message mesg = new Message();
		mesg.setApp("demo app");
		mesg.setModule("kafka");
		mesg.setId(UUID.randomUUID().toString());
		mesg.setData(JsonUtils.toMap(randomUser()));
		
		mesgProducer.send("testrep3", mesg.getId(), JsonUtils.toJson(mesg));
	}
	
	private User randomUser() {
		User user = new User();
		user.setId(rand.nextInt(100000) + 1L);
		user.setName("zhangsan");
		user.setProfile("/path/to/profile");
		
		return user;
	}

}
