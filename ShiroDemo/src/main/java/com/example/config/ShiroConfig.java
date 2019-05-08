package com.example.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.controlle.ShiroRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;

@Configuration
public class ShiroConfig {
	
	private static final String ENCRYPTION = "MD5"; // 散列算法:这里使用md5算法;
	private static final int SUM = 1024; // 散列的次数，比如散列两次，相当于 md5(md5(""));
	
	//生成密码
	 /*public static void main(String[] args) {
	String credentials = "admin";// 密码
	ByteSource credentialsSalt = ByteSource.Util.bytes("admin");//根据用户名生成盐
	//加密方式 盐值 密码 加密次数
	Object obj = new SimpleHash(ENCRYPTION, credentials, credentialsSalt,SUM);
	System.out.println(obj);
    }*/

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置securityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 登录的url
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后跳转的url
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权url
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		// 定义filterChain，静态资源不拦截
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		// druid数据源监控页面不拦截
		filterChainDefinitionMap.put("/druid/**", "anon");
		// 配置退出过滤器，其中具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/", "anon");
		// 除上以外所有url都必须认证通过才可以访问，未通过认证自动访问LoginUrl
		//filterChainDefinitionMap.put("/**", "authc");//需要认证,适用于关键操作 如:支付
		filterChainDefinitionMap.put("/**", "user");//不需要认证,登录过就行

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 配置SecurityManager
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//注入shiroRealm
		securityManager.setRealm(shiroRealm());
		//设置cookie对象
		securityManager.setRememberMeManager(rememberMeManager());
		//session会话
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	public ShiroRealm shiroRealm() {
		// 配置Realm，需自己实现
		ShiroRealm shiroRealm = new ShiroRealm();
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return shiroRealm;
	}

	/**
	 * 凭证匹配器。在myShiroRealm中作用参数使用 登陆时会比较用户输入的密码，跟数据库密码配合盐值salt解密后是否一致。
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName(ENCRYPTION);// 散列算法:这里使用md5算法;
		hashedCredentialsMatcher.setHashIterations(SUM);// 散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

	
	/**
	 * cookie对象
	 * @return
	 */
	public SimpleCookie rememberMeCookie() {
	    // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
	    SimpleCookie cookie = new SimpleCookie("rememberMe");
	    // 设置cookie的过期时间，单位为秒，这里为一天
	    cookie.setMaxAge(60*60*24);
	    return cookie;
	}
	/**
	 * cookie管理对象
	 * @return
	 */
	public CookieRememberMeManager rememberMeManager() {
	    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	    cookieRememberMeManager.setCookie(rememberMeCookie());
	    // rememberMe cookie加密的密钥 
	    cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
	    return cookieRememberMeManager;
	}
	
	/**
	 * 开启注解支持
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
	    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
	    return authorizationAttributeSourceAdvisor;
	}
	
	/**
	 * 启用shiro方言，这样能在页面上使用shiro标签
	 * @return
	 */
	@Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
	
	/**
	 * session会话
	 * @return
	 */
	@Bean
	public SessionDAO sessionDAO() {
	    MemorySessionDAO sessionDAO = new MemorySessionDAO();
	    return sessionDAO;
	}
	
	/**
	 * session会话管理
	 * @return
	 */
	@Bean
	public SessionManager sessionManager() {
	    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
	    Collection<SessionListener> listeners = new ArrayList<SessionListener>();
	    listeners.add(new ShiroSessionListener());
	    sessionManager.setSessionListeners(listeners);
	    sessionManager.setSessionDAO(sessionDAO());
	    return sessionManager;
	}
}