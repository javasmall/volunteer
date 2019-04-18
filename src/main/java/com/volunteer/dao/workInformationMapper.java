package com.volunteer.dao;

import com.volunteer.pojo.workInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface workInformationMapper {
    boolean deleteByPrimaryKey(@Param("id") Integer id,@Param("publisherid") long publisherid);

    boolean insert(workInformation record);

    boolean insertSelective(workInformation record);

    workInformation selectByPrimaryKey(Integer id);
    List<workInformation>selectByteacherid(long publisherid);

    List<workInformation>getallworkinformation(@Param("id") String id,@Param("campus") String campus,@Param("department") String department);
    boolean updateByPrimaryKeySelective(workInformation record);

    boolean updateByPrimaryKey(workInformation record);

    boolean updateneednumdeleteone(String workid);
}