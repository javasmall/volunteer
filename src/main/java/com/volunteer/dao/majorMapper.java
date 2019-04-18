package com.volunteer.dao;

import com.volunteer.pojo.major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface majorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(major record);

    int insertSelective(major record);

    major selectByPrimaryKey(Integer id);

    List<major>getallmajor(String college);
    int updateByPrimaryKeySelective(major record);

    int updateByPrimaryKey(major record);

    @Select("select college from major where major=#{major}")
    String getschoolbymajor(String major);
}