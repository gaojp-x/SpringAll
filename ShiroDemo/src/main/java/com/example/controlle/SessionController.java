package com.example.controlle;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.UserOnline;
import com.example.service.SessionService;
import com.example.utils.JsonResult;

@Controller
@RequestMapping("/online")
public class SessionController {
	
	private static final Logger log = LoggerFactory.getLogger(SessionController.class);
	
    @Autowired
    SessionService sessionService;
    
    @RequestMapping("index")
    public String online() {
        return "userAdmin/online";
    }

    @ResponseBody
    @RequestMapping("list")
    public List<UserOnline> list() {
        return sessionService.list();
    }

    @ResponseBody
    @RequestMapping("forceLogout")
    public String forceLogout(String id) {
        try {
            sessionService.forceLogout(id);
            return JsonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(-1, "踢出失败", null);
        }
    }
}