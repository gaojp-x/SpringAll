package com.example.controlle;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.UserInfo;
import com.example.utils.JsonResult;
import com.example.utils.MyExceptionHandler;

@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public String login(String username, String password,Boolean rememberMe) {
		// 获取Subject对象
		Subject currentUser = SecurityUtils.getSubject();
		// 验证用户是否验证，即是否登录
		if (currentUser.isAuthenticated()) {
			log.info("用户 {} 已验证,正在登录...", username);
			return JsonResult.success();
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
		try {
			currentUser.login(token);
			log.info("{}登录系统....",currentUser.getPrincipal());
			return JsonResult.success();
		} catch (UnknownAccountException e) {
			return JsonResult.error(-1, e.getMessage(), null);
		} catch (IncorrectCredentialsException e) {
			return JsonResult.error(-1, e.getMessage(), null);
		} catch (LockedAccountException e) {
			return JsonResult.error(-1, e.getMessage(), null);
		} catch (AuthenticationException e) {
			return JsonResult.error(-1, "认证失败！", null);
		}

	}

	@RequestMapping("/")
	public String redirectIndex() {
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String index(Model model) throws Exception{
		// 登录成后，即可通过Subject获取登录的用户信息
		UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "index";
	}
}