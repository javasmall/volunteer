package com.volunteer.controller;

import com.volunteer.Result.Result;
import com.volunteer.dao.studentMapper;
import com.volunteer.pojo.campus;
import com.volunteer.pojo.college;
import com.volunteer.pojo.department;
import com.volunteer.pojo.major;
import com.volunteer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("public")
public class publicController {

    @Autowired(required = false)
    campusService campusService;
    @Autowired(required = false)
    departmentService departmentService;
    @Autowired(required = false)
    collegeService collegeService;
    @Autowired(required = false)
    majorService majorService;
    @Autowired(required = false)
    workService workService;
    @Autowired(required = false)
    studentService studentService;
    @Autowired(required = false)
    workAttendService workAttendService;
    @Autowired(required = false)
    studentMapper studentMapper;

    @GetMapping("getallcampus")
    public List<campus>getcampus()
    {
        return campusService.getcampus();
    }
    @GetMapping("getalldepartment")
    public  List<department>getdepartment()
    {
        return departmentService.getalldepartment();
    }
    @GetMapping("getallcollege")
    public List<college>getallcolege()
    {
        return collegeService.getallcollege();
    }
    @GetMapping("getmajorbycollege")
    public List<major>getmajorbycollege(String college)
    {
        return majorService.getmajorbycollege(college);
    }
    @GetMapping("getworkinformation")
    public Result<Object>getworkinformation(String id,String campus,String department)
    {
        return Result.success(workService.getworkinformation(id,campus,department));
    }
    @GetMapping("getallstudentinfo")
    public Result<Object>getallstudentinfo()
    {
        try {
            return Result.success(studentMapper.getallstudentinfo());
        }
        catch (Exception e)
        {
            e.printStackTrace();return Result.error(false);
        }
    }
    @GetMapping("getstudentinformation")
    public Result<Object>getstudentinformation(String studentid,String name,String sex,String college,String major,String inyear)
    {
        return studentService.getallstudent(studentid,name,sex,college,major,inyear);
    }
    @GetMapping("getworkattendbyworkid")
    public Result<Object>getworkattendbyworkid(String workid)
    {

        try {
            if (workid.equals(null)) {
                return Result.error("请给出参数", false);
            }
            else
                return Result.success(workAttendService.getworkattendbyid(workid));
        }catch (Exception e)
        {
            return Result.error("请给出参数", false);
        }
    }
}
