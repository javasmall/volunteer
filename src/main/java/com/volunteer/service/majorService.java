package com.volunteer.service;

import com.volunteer.dao.majorMapper;
import com.volunteer.pojo.major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class majorService {
    @Autowired(required = false)
    majorMapper majorMapper;
    public List<major>getmajorbycollege(String college)
    {
        return majorMapper.getallmajor(college);
    }
}
