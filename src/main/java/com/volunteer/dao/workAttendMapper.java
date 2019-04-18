package com.volunteer.dao;

import com.volunteer.pojo.workAttend;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface workAttendMapper {
   boolean deleteByPrimaryKey(Integer id);

    int insert(workAttend record);

    int insertSelective(workAttend record);

    List<workAttend> selectByPrimaryKey(Long studentid);

    List<workAttend>getallworkattend(String workid);

    int updateByPrimaryKeySelective(workAttend record);

    int updateByPrimaryKey(workAttend record);

    boolean deleteattendwork(@Param("workid") int workid,@Param("studentid") long studentid);

    @Delete("delete from work_attend where work_id=#{id}")
    boolean deletebyworkid(int id);

    @Update("update work_attend set isfinished=1 where work_id=#{workid} and student_id=#{studentid}")
    boolean updatework(@Param("workid") int workid,@Param("studentid")  long studentid);

    @Select("select * from work_attend where student_id=#{studentid} and isfinished =0 " +
            "order by id desc")
    List<workAttend> getworkfinishedbystuid(long studentid);

    @Select("SELECT a.student_id,a.isfinished,SUM(b.work_hour) sum1 " +
            "FROM work_attend a,work_information b " +
            "WHERE DATE_FORMAT(b.start_time,'%Y-%m-%d') BETWEEN #{start} AND #{end} " +
            "AND a.work_id=b.id " +
            "AND a.isfinished=1 " +
            "AND a.student_id=#{studentid} " +
            "GROUP BY a.student_id,a.isfinished")
    Map<String,Object> getworkhourthisterm(@Param("studentid")long studnetid,@Param("start") Date time,@Param("end") Date date);

    @Select("SELECT a.student_id,a.isfinished,SUM(b.work_hour) sum1 FROM work_attend a,work_information b " +
            "WHERE a.work_id=b.id  " +
            "AND a.isfinished=1  " +
            "AND a.student_id=#{studentid} GROUP BY a.student_id,a.isfinished ")
    Map<String, Object> getallworktime(long studentid);
}