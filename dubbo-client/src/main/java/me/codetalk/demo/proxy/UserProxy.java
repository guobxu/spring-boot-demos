package me.codetalk.demo.proxy;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.codetalk.demo.pojo.User;
import me.codetalk.demo.service.IUserService;

@RestController
public class UserProxy {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/auth/user/{username}", method = RequestMethod.GET)
	public Callable<User> getUser(@PathVariable String username, HttpServletRequest request) {

		return () -> {
			return userService.getUser(username);
		};
		
	}
	
}
