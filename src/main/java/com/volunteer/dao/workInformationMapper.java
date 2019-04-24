package com.volunteer.dao;

import com.volunteer.pojo.workInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface workInformationMapper {
    boolean deleteByPrimaryKey(@Param("id") Integer id,@Param("publisherid") long publisherid);

    boolean insert(workInformation record);

    boolean insertSelective(workInformation record);

    workInformation selectByPrimaryKey(Integer id);
    List<workInformation>selectByteacherid(@Param("publisherid") long publisherid,@Param("workid") String workid,@Param("campus") String campus,@Param("department") String department);

    List<workInformation>getallworkinformation(@Param("id") String id,@Param("campus") String campus,@Param("department") String department);
    boolean updateByPrimaryKeySelective(workInformation record);

    boolean updateByPrimaryKey(workInformation record);

    boolean updateneednumdeleteone(String workid);

    @Update("update work_information set status=2 where id=#{id} and publisher_id=#{publisherid}")
    boolean cancelwork(@Param("id") int id,@Param("publisherid") long publisherid);
}