package com.volunteer.service;

import com.volunteer.dao.departmentMapper;
import com.volunteer.pojo.department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class departmentService {
    @Autowired(required = false)
    departmentMapper departmentMapper;
    public List<department>getalldepartment()
    {
        return departmentMapper.getalldepartment();
    }

}
