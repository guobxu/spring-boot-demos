package me.codetalk.demo.main;

import java.util.Random;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import me.codetalk.demo.mapper.UserMapper;
import me.codetalk.demo.pojo.User;

@SpringBootApplication
@EnableScheduling
@ComponentScan(value = {"me.codetalk.mybatis.interceptor"})
@MapperScan(value = {"me.codetalk.demo.mapper"})
public class DemoMain {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);
	
	@Autowired
	private UserMapper userMapper;
	
	private Random rand = new Random();
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoMain.class, args);
    }
    
    @Scheduled(fixedDelay = 6 * 1000 * 1000)
    public void testInteceptor(){
    	Long userId1 = rand.nextInt(10000) + 1L, 
    		 userId2 = rand.nextInt(10000) + 10001L;
    	
    	User user1 = randomUser(userId1), user2 = randomUser(userId2);
    	userMapper.insertUser(user1);
    	userMapper.insertUser(user2);
    	
    	Long newUserId1 = rand.nextInt(10000) + 1L, 
       		 newUserId2 = rand.nextInt(10000) + 10001L;
    	user1.setLogin("user" + newUserId1);
    	user2.setLogin("user" + newUserId2);
    	
    	userMapper.updateUser(user1);
    	userMapper.updateUser(user2);
    	
    	userMapper.deleteUser(user1.getId());
    	userMapper.deleteUser(user2.getId());
    }

    
    private User randomUser(Long userId) {
    	User user = new User();
    	user.setId(userId);
    	user.setLogin("user" + userId);
    	
    	return user;
    }
    
    
}