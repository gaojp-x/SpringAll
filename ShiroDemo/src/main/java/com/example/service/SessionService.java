package com.example.service;

import java.util.List;


import com.example.model.UserOnline;

public interface SessionService {
	
	List<UserOnline> list();

	boolean forceLogout(String sessionId);
}
