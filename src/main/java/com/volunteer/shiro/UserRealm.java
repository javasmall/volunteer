package com.volunteer.shiro;

import com.volunteer.dao.usersMapper;
import com.volunteer.pojo.users;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 自定义Realm
 * @author lenovo
 *
 */
public class UserRealm extends AuthorizingRealm{

	@Autowired (required = false)
	private usersMapper usersMapper;
	private final Logger logger= LoggerFactory.getLogger(UserRealm.class);
	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		logger.info("执行逻辑授权");

		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		//添加资源的授权字符串
		//info.addStringPermission("user:add");

		//到数据库查询当前登录用户的授权字符串
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		users user = (users) subject.getPrincipal();
		users dbUser = usersMapper.selectByPrimaryKey(user.getId());

		info.addStringPermission("user:"+dbUser.getRole());
		System.out.println("user:"+dbUser.getRole());

		return info;
	}
	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		//编写shiro判断逻辑，判断用户名和密码
		//1.判断用户名
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;
		
		users user = usersMapper.getuserbystudentid(Long.parseLong(token.getUsername()));

		if(user==null){
			//用户名不存在
			return null;//shiro底层会抛出UnKnowAccountException
		}
		//2.判断密码
		return new SimpleAuthenticationInfo(user,user.getPassword(),"");
	}

}
