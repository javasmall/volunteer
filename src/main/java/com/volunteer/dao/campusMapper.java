package com.volunteer.dao;

import com.volunteer.pojo.campus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface campusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(campus record);

    int insertSelective(campus record);

    campus selectByPrimaryKey(Integer id);
    List<campus>selectall();
    int updateByPrimaryKeySelective(campus record);

    int updateByPrimaryKey(campus record);
}