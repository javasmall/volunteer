package com.volunteer.dao;

import com.volunteer.pojo.publisher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface publisherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(publisher record);

    boolean insertSelective(publisher record);

    publisher selectByPrimaryKey(Integer id);

    List<publisher>getallpublisher();
    boolean updateByPrimaryKeySelective(publisher record);

    int updateByPrimaryKey(publisher record);

    @Delete("delete from publisher where publisher_id=#{publisherid}")
    boolean deletebypublisherid(long publisherid);

    @Select("select * from publisher where publisher_id=#{publisherid}")
    publisher getpublisherbypublid(Long publisherid);
}