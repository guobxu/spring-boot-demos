package me.codetalk.demo.main;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import me.codetalk.demo.Constants;
import me.codetalk.demo.messaging.MesgObj;
import me.codetalk.demo.messaging.MessagingUtil;
import me.codetalk.demo.messaging.redis.pub.IMessagePublisher;
import me.codetalk.demo.pojo.Order;
import me.codetalk.demo.pojo.User;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {
		"me.codetalk.cache.config",
        "me.codetalk.demo.messaging.redis.config",
        "me.codetalk.demo.messaging.redis.pub",
        "me.codetalk.demo.messaging.redis.sub",
})
public class DemoApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoApp.class);
	
	private static Random rand = new Random();
	
	@Autowired
	private IMessagePublisher mesgPublisher;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApp.class);
	}

	@Scheduled(initialDelay = 5* 1000, fixedDelay = 6 * 1000)
	public void testUserSend() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("User " + rand.nextInt(2000));
		user.setBirth(System.currentTimeMillis());
		
		MesgObj msgobj = MessagingUtil.convertAsMesgObj(Constants.MSGTYPE_USER_CREATE, user);
		
		mesgPublisher.sendMessage(Constants.CHN_USER_CREATE, msgobj);
	}
	
	@Scheduled(initialDelay = 5* 1000, fixedDelay = 3 * 1000)
	public void testOrderSend() {
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setNo("Order-No-" + rand.nextInt(99999));
		order.setCreateDate(System.currentTimeMillis());
		
		MesgObj msgobj = MessagingUtil.convertAsMesgObj(Constants.MSGTYPE_ORDER_CREATE, order);
		
		mesgPublisher.sendMessage(Constants.CHN_ORDER_CREATE, msgobj);
	}

}
















