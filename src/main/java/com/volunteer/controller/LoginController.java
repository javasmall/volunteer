package com.volunteer.controller;

import com.volunteer.Result.Result;
import com.volunteer.dao.usersMapper;
import com.volunteer.pojo.users;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);
   @Autowired(required = false)
   @ResponseBody
    @PostMapping("login")
    public Result<Object> login(String username, String password, HttpServletRequest request)
    {
        Map cookie=new HashMap<>();
        cookie.put("JSESSIONID",request.getSession().getId());
        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        logger.info(username+" "+password);
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        //3.执行登录方法
        try {
            subject.login(token);

            //登录成功
            //跳转到test.html
            return Result.success(cookie);
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在
           return Result.error("用户名不存在");
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败:密码错误
            return Result.error("密码错误");
        }
    }
    @ResponseBody
    @GetMapping("noauth")
    public Result<String> noauth()
    {
        return Result.error((String)"请求失败，请先登录");
    }
}
