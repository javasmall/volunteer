package com.volunteer.service;

import com.volunteer.dao.collegeMapper;
import com.volunteer.pojo.college;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class collegeService {
    @Autowired(required = false)
    collegeMapper collegeMapper;
    public List<college>getallcollege()
    {
        return collegeMapper.getallcollege();
    }

}
