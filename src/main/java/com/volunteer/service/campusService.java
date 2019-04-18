package com.volunteer.service;

import com.volunteer.dao.campusMapper;
import com.volunteer.pojo.campus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class campusService {
    @Autowired(required = false)
    campusMapper campusMapper;
    public List<campus>getcampus()
    {
        return campusMapper.selectall();
    }
}
