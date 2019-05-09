package com.gao.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gao.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService us;

	@RequestMapping("/selectUser")
	public String selectUser(@RequestParam Map<String, String> map, HttpServletRequest request) {
		System.out.println(map);
		Map<String, Object> user = us.selectUser(map);
		System.out.println(user);
		return user.toString();
	}

}
