package com.gao.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.mapper.UserMapper;
import com.gao.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper um;

	@Override
	public Map<String,Object> selectUser(Map<String, String> map) {
		return um.selectUser(map);
	}

}
