package com.volunteer.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Shiro的配置类
 * @author lenovo
 *
 */
@Configuration
public class ShiroConfig {

	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		
		//添加Shiro内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 *    常用的过滤器：
		 *       anon: 无需认证（登录）可以访问
		 *       authc: 必须认证才可以访问
		 *       user: 如果使用rememberMe的功能可以直接访问
		 *       perm： 该资源必须得到资源权限才可以访问
		 *       role: 该资源必须得到角色权限才可以访问
		 */
		Map<String, Filter> filtersMap = new LinkedHashMap<>();
		filtersMap.put("rolesFilter",new rolesFilter());
		shiroFilterFactoryBean.setFilters(filtersMap);
		Map<String,String> filterMap = new LinkedHashMap<String,String>();

//		filterMap.put("/testThymeleaf", "anon");
		//放行login.html页面
//		filterMap.put("/**","anon");

		filterMap.put("/index.html","anon");
		filterMap.put("/swagger-ui.html","anon");
		filterMap.put("/login", "anon");//要将登陆的接口放出来，不然没权限访问登陆的接口
////		filterMap.put("/getcontroller","anon");
////
//		//授权过滤器
//		//注意：当前授权拦截后，shiro会自动跳转到未授权页面
        filterMap.put("/admin/**","roles[admin]");
        filterMap.put("/teacher/**","rolesFilter[publisher,admin]");
        filterMap.put("/student/**","rolesFilter[student,admin]");

		filterMap.put("/**", "authc");//authc即为认证登陆后即可访问
//		//修改调整的登录页面
		shiroFilterFactoryBean.setLoginUrl("/noauth");
//		//设置未授权提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/norole");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		
		
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(userRealm);
		return securityManager;
	}
	
	/**
	 * 创建Realm
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm(){
		return new UserRealm();
	}

//	public rolesFilter rolesFilter()
//	{
//		return  new rolesFilter();
//	}

}
