package com.volunteer.controller;

import com.volunteer.Result.Result;
import com.volunteer.check.checkService;
import com.volunteer.dao.workInformationMapper;
import com.volunteer.pojo.workInformation;
import com.volunteer.service.publisherService;
import com.volunteer.service.workService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/teacher")
public class teacherController {

    @Autowired(required = false)
    checkService checkService;
    @Autowired(required = false)
    workService workService;
    @Autowired(required = false)
    publisherService publisherService;
    @Autowired(required = false)
    workInformationMapper workInformationMapper;
    private boolean jud(String workname) {
        if(workname==null||"".equals(workname))return true;//为 null
        else return false;
    }

    @PostMapping("createactivity")
    public Result<workInformation>createactivity(String workname,  long publisher_id, String campus, String department,
                                         String address, String tip, Double work_hour,int neednumber, String start_time)
    {
        workInformation work = new workInformation();
        try {
            work.setAttendNum(0);
            work.setName(workname);
            work.setPublisherId(publisher_id);
            work.setWorkCampus(campus);
            work.setWorkDepartment(department);
            work.setWorkAddr(address);
            work.setWorkTip(tip);
            work.setWorkHour(work_hour);
            work.setNeedNum(neednumber);
            work.setStartTime(start_time);
            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
            work.setPublishTime(time);
            if (work_hour > 2 || work_hour < 0) {
                return Result.error("工时错误", work);
            }
            workService.insertwork(work);
            return Result.success(work);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.error("请查看参数是否正确",work);
        }
    }
    @GetMapping("getallworkbyteacherid")
    public Result<Map> getallworkbyteacherid(String publisherid,String workid,String campus,String department)
    {
        Map<String,Object> work=new HashMap();
        if(publisherid.equals(null)){return Result.error("请检查参数",work);}
          try {
              work=workService.getallworkbyteacherid(Long.parseLong(publisherid),workid,campus,department);
              return Result.success(work);
          }
          catch (Exception e)
          {return  Result.error(work);}
    }
    @PostMapping("updateactivity")
    public Result<Object>updateactivity(String id,String workname, String campus, String department,
                                                 String address, String tip, String work_hour,String neednumber, String start_time)
    {

        workInformation work = new workInformation();
        if(id==null||"".equals(id))return Result.error("请给出活动编号");
        try {
            work=workInformationMapper.selectByPrimaryKey(Integer.parseInt(id));

           if(!jud(workname)) work.setName(workname);
           if(!jud(campus)) work.setWorkCampus(campus);
           if(!jud(department)) work.setWorkDepartment(department);
            if(!jud(address)) work.setWorkAddr(address);
            if(!jud(tip))work.setWorkTip(tip);
            if(!jud(work_hour)) {
                if(!checkService.checkwork_time(Double.parseDouble(work_hour)))
                    return Result.error("工时错误",false);
                else
                work.setWorkHour(Double.parseDouble(work_hour));
            }
            if(!jud(neednumber)){
                 if(Integer.parseInt(neednumber)>=work.getAttendNum())
                work.setNeedNum(Integer.parseInt(neednumber));
            }
            work.setStartTime(start_time);
            workService.updatework(work);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.error("参数错误",work);
        }
        return Result.success(true);
    }



    @PostMapping("deleteactivity")//删除活动
    public Result<Object>dedeteactivity(String id,String publisherid)
    {
        try {
            return workService.deletework(Integer.parseInt(id),Long.parseLong(publisherid));
        }
        catch (Exception e)
        {
            return  Result.error("请检查参数");
        }
    }
    @PostMapping("updatepublisherinformation")
    public Result<Object>updatepublisherinformation(String publisherid,String name,String phone,String location)
    {
        try {
            return publisherService.updatepublisherinformation(publisherid,name,phone,location);
        }catch (Exception e){e.printStackTrace();return Result.error(false);}
    }
    @PostMapping("updatepublisherpassword")
    public Result<Object>updatepublisherpassword(String publisherid,String oldpassword,String newpassword)
    {
        try {
            return publisherService.updatepublisherpassword(publisherid,oldpassword,newpassword);
        }catch (Exception e){e.printStackTrace();return Result.error(false);}
    }
    /*
    核实义工活动
     */
    @PostMapping("checkactivity")
    public Result<Object>checkactivity(int workid,long publisherid,long studentid[])
    {
        try {
            return  publisherService.checkactivity(workid,publisherid,studentid);
        }catch (Exception e)
        {
            e.printStackTrace();return Result.error(false);
        }
    }
}
