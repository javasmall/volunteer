package com.volunteer.service;

import com.volunteer.dao.usersMapper;
import com.volunteer.pojo.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class userService {

    @Autowired(required = false)
    usersMapper userMapper;
    public users getuserbyusername(String username) {
        return userMapper.getuserbystudentid(Long.parseLong(username));
    }
}
