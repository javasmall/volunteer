package com.volunteer.dao;

import com.volunteer.pojo.users;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface usersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(users record);

    boolean insertSelective(users record);

    users selectByPrimaryKey(Integer id);

    List<users>getallusers();
    boolean updateByPrimaryKeySelective(users record);

    int updateByPrimaryKey(users record);
    @Delete("delete from users where username=#{studentid}")
    boolean deletebyusername(long studentid);

    @Select("select * from users where username=#{username}")
    users getuserbystudentid(long username);
}