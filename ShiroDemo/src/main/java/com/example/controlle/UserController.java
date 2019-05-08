package com.example.controlle;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.UserInfo;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequiresPermissions("user:user")
    @RequestMapping("list")
    public String userList(Model model) {
        model.addAttribute("value", "获取用户信息");
        return "userAdmin/user";
    }
    
    @RequiresPermissions("user:user")
    @RequestMapping("userAdmin")
    public String userAdmin(Model model) {
    	UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
    	return "userAdmin/userAdmin.html";
    }
    
    @RequiresPermissions("user:add")
    @RequestMapping("add")
    public String userAdd(Model model) {
        model.addAttribute("value", "新增用户");
        return "userAdmin/user";
    }
    
    @RequiresPermissions("user:delete")
    @RequestMapping("delete")
    public String userDelete(Model model) {
        model.addAttribute("value", "删除用户");
        return "userAdmin/user";
    }
}