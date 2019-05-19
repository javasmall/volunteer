package com.volunteer.controller;

import com.volunteer.Result.Result;
import com.volunteer.pojo.users;
import com.volunteer.pojo.workAttend;
import com.volunteer.service.studentService;
import com.volunteer.service.workAttendService;
import com.volunteer.service.workService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class studentController {
    @Autowired(required = false)
    workAttendService workAttendService;
    @Autowired(required = false)
    workService workService;
    @Autowired(required = false)
    studentService studentService;
    @PostMapping("orderwork")
    public Result<Object>orderwork(String workid)
    {
        Subject subject = SecurityUtils.getSubject();
        users student=(users)subject.getPrincipal();

        try {
           Boolean jud= workAttendService.insertworkattend(workid,student.getUsername());
           if(jud)
               return Result.success(jud);
           else
               return Result.error("预约失败",false);
        }
        catch (Exception e){e.printStackTrace();return Result.error(false);}
    }
    @GetMapping("getattendfinishbystuid")
    public  List<workAttend> getattendfinishbystuid(long studentid)
    {
        try {
            return  workAttendService.getworkfinishedbystuid(studentid);
        }catch (Exception e)
        {
            return  null;
        }
    }
    @GetMapping("getattendbystuid")
    public List<workAttend> getattendbystuid(long studentid)
    {
        try {
            return workAttendService.getattendbystuid(studentid);
        }
        catch (Exception e){return null;}

    }
    @PostMapping("deleteattendwork")
    public Result<Object>deleteattendwork(String workid)
    {
        Subject subject = SecurityUtils.getSubject();
        users student=(users)subject.getPrincipal();
        if(workAttendService.deleteattendwork(workid,student.getUsername()))
            return Result.success(true);
        else
        return Result.error(false);
    }

    @PostMapping("updatestudentinformation")
    public Result<Object>updatestudentinformation(String studentid,String phone,String major,String sex,String inyear)
    {
        try {
            return studentService.updateuser(studentid,phone,major,inyear,sex);
        }
        catch (Exception e)
        {e.printStackTrace();return  Result.error(false);}
    }
    @PostMapping("updatestudentpassword")
    public  Result<Object>updatestudentpassword(String studentid,String oldpassword,String newpassword)
    {
        try {
            return studentService.updatepassword(studentid,oldpassword,newpassword);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.error(false);
        }
    }
    @GetMapping("getworktimethisterm")
    public Result<Object>getworktimecountthisterm(String studentid)
    {
        try {
            return workAttendService.getworktimethisterm(Long.parseLong(studentid));
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.error(false);
        }
    }
    @GetMapping("getallworktime")
    public Result<Object>getallworktime(String studentid)
    {
        try {
            return workAttendService.getallworkhour(studentid);
        }
        catch (Exception e)
        {
            return Result.error(false);
        }
    }
    @GetMapping("getworktimeselect")
    public Result<Object>getallworktimeselect(String studentid,String starttime,String endtime)
    {
        try {
            return workAttendService.getworktimeselect(studentid,starttime,endtime);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.error(false);
        }
    }

}
