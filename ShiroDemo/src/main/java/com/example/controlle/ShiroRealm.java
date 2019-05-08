package com.example.controlle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.mapper.UserMapper;
import com.example.mapper.UserPermissionMapper;
import com.example.mapper.UserRoleMapper;
import com.example.model.Permission;
import com.example.model.Role;
import com.example.model.UserInfo;
import com.example.utils.MyExceptionHandler;

public class ShiroRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

	@Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// 将AuthenticationToken强转为AuthenticationToken对象
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		// 获得从表单传过来的用户名
		String userName = upToken.getUsername();

		// 通过用户名到数据库查询用户信息
		UserInfo userInfo = userMapper.findByUserName(userName);

		// 加密用的盐值，此处用用户名
		ByteSource credentialsSalt = ByteSource.Util.bytes(userName);

		if (userInfo == null) {
			throw new UnknownAccountException("用户名或密码错误！");
		}
		if (userInfo.getStatus().equals("0")) {
			throw new LockedAccountException("账号已被锁定,请联系管理员！");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), credentialsSalt,getName());
		// 从数据库查看是否存在用户
		log.info("用户{}认证-----ShiroRealm.doGetAuthenticationInfo",userName);
		return info;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		//Object principal = principals.getPrimaryPrincipal();
		//UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		UserInfo user = (UserInfo) principals.getPrimaryPrincipal();
		
		String userName = user.getUserName();
        
        log.info("用户{}获取权限-----ShiroRealm.doGetAuthorizationInfo",userName);
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        // 获取用户角色集
        List<Role> roleList = userRoleMapper.findByUserName(userName);
        Set<String> roleSet = new HashSet<String>();
        for (Role r : roleList) {
            roleSet.add(r.getName());
        }
        info.setRoles(roleSet);
        
        // 获取用户权限集
        List<Permission> permissionList = userPermissionMapper.findByUserName(userName);
        Set<String> permissionSet = new HashSet<String>();
        for (Permission p : permissionList) {
            permissionSet.add(p.getName());
        }
        info.setStringPermissions(permissionSet);
        return info;
	}

}
