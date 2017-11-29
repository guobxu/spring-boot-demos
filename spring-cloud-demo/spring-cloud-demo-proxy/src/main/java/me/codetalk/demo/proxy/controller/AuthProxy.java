package me.codetalk.demo.proxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.codetalk.demo.proxy.client.AuthClient;

@RestController
public class AuthProxy {

	@Autowired
	private AuthClient authClient;
	
	@RequestMapping(value = "/auth/user/{username}", method = RequestMethod.GET)
    public String doGet(@PathVariable("username") String username) throws Exception {
		return authClient.doGet(username);
    }
	
}
