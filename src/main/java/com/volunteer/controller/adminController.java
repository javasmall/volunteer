package com.volunteer.controller;

import com.volunteer.Result.Result;
import com.volunteer.service.publisherService;
import com.volunteer.service.studentService;
import com.volunteer.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class adminController {

    @Autowired(required = false)
    userService userService;
    @Autowired(required = false)
    studentService studentService;
    @Autowired(required = false)
    publisherService publisherService;
    @PostMapping("addstudentuser")
    public Result<Object>addstudentuser(String studentid,String password,String name,String sex,String major,String phone,String inyear)
    {
        try {
            if (studentid.equals(null) || sex.equals(null) || major.equals(null) ||
                    password.equals(null)||phone.equals(null) || inyear.equals(null)||name.equals(null)) {
                return Result.error("请核实信息完整");
            } else {
                return studentService.addstudentuser(studentid,password,name, sex, major, phone, inyear);
            }
        }
        catch (Exception e){e.printStackTrace();return Result.error(false);}

    }
    @PostMapping("addpublisher")
    public Result<Object>addstudentuser(String publisherid,String password,String name,String phone,String location)
    {
        try {
            if(publisherid.equals("")||name.equals("")||phone.equals("")||location.equals("")||password.equals(""))
            {
                return Result.error("请检查信息完整性",false);
            }
            else
            {
                return publisherService.addpublisher(publisherid,password,name,phone,location);
            }
        }catch (Exception e)
        {e.printStackTrace();return Result.error(false);}
    }
    @PostMapping("deletestudentuser")
    public Result<Object>deletestudentuser(String studentid)
    {
        try {
            return studentService.deletestudentuser(Long.parseLong(studentid));
        }
        catch (Exception e)
        {
            e.printStackTrace();return Result.error(false);
        }
    }
    @PostMapping("deletepublisher")
    public Result<Object>deletepublisher(String publisherid)
    {
        try {
            return  publisherService.deletebypublisherid(publisherid);
        }
        catch (Exception e)
        {
            e.printStackTrace();return  Result.error(false);
        }
    }
    @PostMapping("updatestudent")
    public Result<Object>updatestudent(String studentid,String password,String name,String sex,String phone,String major,String inyear)
    {
        try {
            return studentService.updatestubyadmin(studentid,password,name,sex,phone,major,inyear);
        }catch (Exception e)
        {
            e.printStackTrace();return  Result.error(false);
        }
    }
    @PostMapping("updatepublisher")
    public Result<Object>updatepublisher(String publisherid,String name,String password,String phone,String location)
    {
        try {
            return publisherService.updatepublisherbyadmin(publisherid,name,password,phone,location);
        }catch (Exception e)
        {e.printStackTrace();return Result.error(false);}
    }
    @GetMapping("getallpublisher")
    public Result<Object>getallstudent()
    {
        return publisherService.getallpublisher();
    }

}
