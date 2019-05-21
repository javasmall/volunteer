package com.volunteer.dao;

import com.volunteer.pojo.student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface studentMapper {
    int deleteByPrimaryKey(Integer id);

    boolean insert(student record);

    boolean insertSelective(student record);

    student selectByPrimaryKey(long studentid);
    List<Map>getallstudent(@Param("studentid")String studentid, @Param("name")String name, @Param("sex")String sex
                               , @Param("college")String college, @Param("major")String major, @Param("inyear")String inyear);
    int updateByPrimaryKeySelective(student record);

    int updateByPrimaryKey(student record);

    @Delete("delete from student where student_id=#{studentid}")
    boolean deletebyid(long studentid);

    @Select("select * from student")
    List<student> getallstudentinfo();
}