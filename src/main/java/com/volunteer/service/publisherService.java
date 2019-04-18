package com.volunteer.service;

import com.volunteer.Result.Result;
import com.volunteer.dao.publisherMapper;
import com.volunteer.dao.usersMapper;
import com.volunteer.dao.workAttendMapper;
import com.volunteer.dao.workInformationMapper;
import com.volunteer.pojo.publisher;
import com.volunteer.pojo.users;
import com.volunteer.pojo.workInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class publisherService {
    @Autowired(required = false)
    publisherMapper publisherMapper;
    @Autowired(required = false)
    usersMapper usermapper;
    @Autowired(required = false)
    workAttendMapper workAttendMapper;
    @Autowired(required = false)
    workInformationMapper workInformationMapper;

    public Result<Object> addpublisher(String publisherid,String password ,String name, String phone, String location) {
        try {
            publisher publisher=new publisher();publisher.setPublisherId(Long.parseLong(publisherid));
            publisher.setLocation(location);publisher.setName(name);publisher.setPhone(Long.parseLong(phone));
            users user=new users();user.setRole("publisher");user.setUsername(Long.parseLong(publisherid));user.setPassword(password);
            if(usermapper.insertSelective(user)&&publisherMapper.insertSelective(publisher))
            {
                return Result.success(true);
            }
            else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
                return Result.error(false);
            }
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);
        }
    }

    public Result<Object> deletebypublisherid(String publisherid) {
        try {
            if(publisherMapper.deletebypublisherid(Long.parseLong(publisherid)))
            {
                usermapper.deletebyusername(Long.parseLong(publisherid));
                return Result.success(true);
            }
            else {return Result.error(false);}
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);}
    }

    public Result<Object> updatepublisherinformation(String publisherid,String name, String phone, String location) {
        try {
            publisher publisher=publisherMapper.getpublisherbypublid(Long.parseLong(publisherid));
            if(name!=null){publisher.setName(name);}
            if(phone!=null){publisher.setPhone(Long.parseLong(phone));}
            if(location!=null){publisher.setLocation(location);}
            publisherMapper.updateByPrimaryKeySelective(publisher);
            return Result.success(true);
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);
        }
    }


    public Result<Object> updatepublisherpassword(String publisherid, String oldpassword, String newpassword) {
        try {
            publisher publisher=publisherMapper.getpublisherbypublid(Long.parseLong(publisherid));
            if(publisher==null){return Result.error(false);}
            users user=usermapper.getuserbystudentid(Long.parseLong(publisherid));
            if(user.getPassword().equals(oldpassword))
            {
                user.setPassword(newpassword);
                usermapper.updateByPrimaryKeySelective(user);
                return Result.success(true);
            }
            else return Result.error(false);
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);
        }
    }
    public Result<Object> updatepublisherbyadmin(String publisherid, String name, String password, String phone, String location) {
        try {
            publisher publisher=publisherMapper.getpublisherbypublid(Long.parseLong(publisherid));
            users user=usermapper.getuserbystudentid(Long.parseLong(publisherid));
            if(publisher==null||user==null){return Result.error(false);}
            if(password!=null||!"".equals(password)){user.setPassword(password);}
            if(name!=null||!"".equals(name)){publisher.setName(name);}
            if(phone!=null||!"".equals(phone)){publisher.setPhone(Long.parseLong(phone));}
            if(location!=null||!"".equals(location)){publisher.setLocation(location);}
            if(publisherMapper.updateByPrimaryKeySelective(publisher)&&usermapper.updateByPrimaryKeySelective(user))
            {
                return Result.success(true);
            }
            else { TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
                return Result.error(false);}

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);}
    }


    public Result<Object> checkactivity(int workid, long publisherid, long[] studentid) {
        try {
            workInformation workInformation=workInformationMapper.selectByPrimaryKey(workid);
            String date1=workInformation.getStartTime().substring(0,workInformation.getStartTime().length()-2);
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date=sd.parse(date1);
            Date now=new Date();
            long hour=(date.getTime()-now.getTime())/1000/60/60;
            if(hour>0){return Result.error("未到结算时间",false);}
            if(workInformation.getPublisherId()!=publisherid){return Result.error(false);}
            for(int i=0;i<studentid.length;i++)
            {
               if( !workAttendMapper.updatework(workid,studentid[i]))
               {
                   TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                   return Result.error(false);
               }
            }
            return Result.success(true);
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);
        }

    }

    public Result<Object> getallpublisher() {
        try {
            return Result.success(publisherMapper.getallpublisher());
        }catch (Exception e)
        {
            return  Result.error(false);
        }
    }
}
