package me.codetalk.demo.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.codetalk.demo.auth.pojo.User;
import me.codetalk.demo.auth.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	public User getUser(String username) {
//		LOGGER.info("5000 in getUser: " + username);
		try {
			Thread.sleep(10);
		} catch(InterruptedException iex) {
			LOGGER.error(iex.getMessage(), iex);
		}
		
		if("admin".equals(username)) {
			return new User("admin", "welcome1");
		}
		
		return null;
	}

}
