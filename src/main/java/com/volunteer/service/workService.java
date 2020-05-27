package com.volunteer.service;

import com.sun.org.apache.regexp.internal.RE;
import com.volunteer.Result.Result;
import com.volunteer.dao.workAttendMapper;
import com.volunteer.dao.workInformationMapper;
import com.volunteer.pojo.workInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class workService {
    @Autowired(required = false)
    workInformationMapper workInformationMapper;
    @Autowired(required = false)
    workAttendMapper workAttendMapper;
    @Transactional//事务
    public boolean insertwork(workInformation workInformation)
    {
        return  workInformationMapper.insertSelective(workInformation);
    }
    public Map getworkinformation(String id,String campus,String department)
    {
        List<workInformation>list=  workInformationMapper.getallworkinformation(id,campus,department);
        Map<String,List>map=new HashMap<>();
        int i=0,size=list.size();workInformation team=new workInformation();
        List<workInformation>ison=new ArrayList<>();List<workInformation>overtime=new ArrayList<>();
        for(;i<size;i++)
        {
            team=list.get(i);
            team.setStartTime(team.getStartTime().substring(0,team.getStartTime().length()-2));
            team.setPublishTime(team.getPublishTime().substring(0,team.getPublishTime().length()-2));
            Calendar cal = Calendar.getInstance();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
            if(team.getStartTime().compareTo(time)>0)//还能预约
            {
                ison.add(team);
            }
            else {overtime.add(team);}
        }
        map.put("overtime",overtime);map.put("ison",ison);
        return map;
    }

    public Map getallworkbyteacherid(long publisherid, String workid, String campus, String department)
    {

        List<workInformation> list=  workInformationMapper.selectByteacherid(publisherid,workid,campus,department);
        Map<String,List>map=new HashMap<>();
        int i=0,size=list.size();workInformation team=new workInformation();
        List<workInformation>ison=new ArrayList<>();List<workInformation>overtime=new ArrayList<>();
        for(;i<size;i++)
        {
            team=list.get(i);
            team.setStartTime(team.getStartTime().substring(0,team.getStartTime().length()-2));
            team.setPublishTime(team.getPublishTime().substring(0,team.getPublishTime().length()-2));
            Calendar cal2 = Calendar.getInstance();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal2.getTime());
            if(team.getStartTime().compareTo(time)>0)//mei
            {
                 ison.add(team);
            }
            else {overtime.add(team);}
        }
        map.put("overtime",overtime);map.put("ison",ison);
        return map;
    }
    @Transactional
    public Result<Object> updatework(workInformation workInformation) throws ParseException {
        if(workInformation.getStartTime()!=null){
        String date1=workInformation.getStartTime().toString();
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date=sd.parse(date1);
        Date now=new Date();
        long hour=(date.getTime()-now.getTime())/1000/60/60;
        if(hour<8){return Result.error("8小时内不允许修改",false);}
        else {
           return Result.success(workInformationMapper.updateByPrimaryKeySelective(workInformation));
        }}
        else{
            return Result.success(workInformationMapper.updateByPrimaryKeySelective(workInformation));
        }
    }
    @Transactional
    public Result<Object> deletework(int id, long publisherid) throws ParseException {
        workInformation team=workInformationMapper.selectByPrimaryKey(id);
        team.setStartTime(team.getStartTime().substring(0,team.getStartTime().length()-2));

        SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now=new Date();
        Date date=sd.parse(team.getStartTime());
        long hour=(date.getTime()-now.getTime())/1000/60/60;
        if(hour<8){return Result.error("8小时内不允许取消",false);}
        else{
            if (workInformationMapper.cancelwork(id,publisherid))//修改对应的attend表
            {
                workAttendMapper.deletebyworkid(id);
                return Result.success(true);
            }
            return Result.error(false);

        }



    }
//所有活动
    public List<workInformation> getallwork(String workid) {
        if(workid==null)
        return workInformationMapper.getallwork();
        else
        {
            List<workInformation>list=new ArrayList<>();
            list.add(workInformationMapper.selectByPrimaryKey(Integer.parseInt(workid)));
            return list;
        }

    }
}
