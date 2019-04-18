package com.volunteer.dao;

import com.volunteer.pojo.department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface departmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(department record);

    int insertSelective(department record);

    department selectByPrimaryKey(Integer id);

    List<department>getalldepartment();
    int updateByPrimaryKeySelective(department record);

    int updateByPrimaryKey(department record);
}