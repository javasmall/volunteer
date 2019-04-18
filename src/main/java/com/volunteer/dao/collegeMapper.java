package com.volunteer.dao;

import com.volunteer.pojo.college;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface collegeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(college record);

    int insertSelective(college record);

    college selectByPrimaryKey(Integer id);
     List<college>getallcollege();
    int updateByPrimaryKeySelective(college record);

    int updateByPrimaryKey(college record);
}