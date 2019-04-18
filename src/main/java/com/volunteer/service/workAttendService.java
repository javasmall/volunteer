package com.volunteer.service;

import com.volunteer.Result.Result;
import com.volunteer.dao.studentMapper;
import com.volunteer.dao.workAttendMapper;
import com.volunteer.dao.workInformationMapper;
import com.volunteer.pojo.student;
import com.volunteer.pojo.workAttend;
import com.volunteer.pojo.workInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class workAttendService {
    private static final Logger logger= LoggerFactory.getLogger(workAttendService.class);
    @Autowired(required = false)
    workService workService;
    @Autowired(required = false)
    private studentMapper studentMapepr;
    @Autowired(required = false)
    workInformationMapper workInformationMapper;
    @Autowired(required = false)
    workAttendMapper workAttendMapper;
    public List<workAttend>getworkattendbyid(String workid)
    {
        return workAttendMapper.getallworkattend(workid);
    }

    @Transactional
    public boolean insertworkattend(String workid, String studentid) {
        try {
            workInformation workInformation =workInformationMapper.getallworkinformation(workid, null, null).get(0);
            int need=workInformation.getNeedNum();
            int attend=workInformation.getAttendNum();
            student student=studentMapepr.selectByPrimaryKey(Long.parseLong(studentid));
            if(need-attend<=0||student==null)
            {
                return false;
            }
            else
            {
                workInformation.setAttendNum(attend+1);
                workService.updatework(workInformation);
                workAttend workAttend=new workAttend();
                workAttend.setIsfinished(false);
                workAttend.setStudentId(Long.parseLong(studentid));
                workAttend.setStudentName(student.getName());
                workAttend.setWorkId(workInformation.getId());
                workAttend.setWorkName(workInformation.getName());
                workAttendMapper.insert(workAttend);
                return true;
            }
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return false;
        }
    }

    public List<workAttend> getattendbystuid(long studentid) {
        return workAttendMapper.selectByPrimaryKey(studentid);
    }


    public boolean deleteattendwork(String workid, String studentid) {
        try {
            boolean jud=workAttendMapper.deleteattendwork(Integer.parseInt(workid),Long.parseLong(studentid));
            if(!jud){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
                 return false;}
            else
            {
                workInformationMapper.updateneednumdeleteone(workid);
            }
            return true;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return false;
        }
    }

    public List<workAttend> getworkfinishedbystuid(long studentid) {
        return workAttendMapper.getworkfinishedbystuid(studentid);
    }

    public Result<Object> getworktimethisterm(long studnetid) {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

        if(month<2){
            calendar.set(year-1,8-1,30);
        }
        else if(month>=8)
        {
            calendar.set(year,8-1,30);
        }
        else calendar.set(year,2-1,10);
        logger.info(calendar.toString()+" "+new Date());
        return Result.success(workAttendMapper.getworkhourthisterm(studnetid,calendar.getTime(),new Date()));
    }

    public Result<Object> getallworkhour(String studentid) {
        Map<String,Object>map=workAttendMapper.getallworktime(Long.parseLong(studentid));
        return Result.success(map);
    }

    public Result<Object> getworktimeselect(String studentid, String starttime, String endtime) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return Result.success(workAttendMapper.getworkhourthisterm(Long.parseLong(studentid),sd.parse(starttime),sd.parse(endtime)));
    }
}
