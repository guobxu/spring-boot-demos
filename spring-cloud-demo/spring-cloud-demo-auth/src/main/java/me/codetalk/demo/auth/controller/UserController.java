package me.codetalk.demo.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.codetalk.demo.auth.pojo.User;
import me.codetalk.demo.auth.service.IUserService;

@RestController
public class UserController {

	@Autowired
	private IUserService userService;

//	@RequestMapping(value = "/auth/user/{username}", method = RequestMethod.GET)
//	public Callable<User> getUser(@PathVariable String username, HttpServletRequest request) {
//
//		return () -> {
//			return userService.getUser(username);
//		};
//		
//	}
	
	@RequestMapping(value = "/auth/user/{username}", method = RequestMethod.GET)
	public User getUser(@PathVariable String username, HttpServletRequest request) {

		return userService.getUser(username);
		
	}
}
