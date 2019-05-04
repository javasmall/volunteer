package com.volunteer.service;

import com.volunteer.Result.Result;
import com.volunteer.dao.majorMapper;
import com.volunteer.dao.studentMapper;
import com.volunteer.dao.usersMapper;
import com.volunteer.pojo.student;
import com.volunteer.pojo.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class studentService {
    @Autowired(required = false)
    studentMapper studentMapper;
    @Autowired(required = false)
    majorMapper majorMapepr;
    @Autowired(required = false)
    usersMapper usersmapper;
    public Result<Object> getallstudent(String studentid, String name, String sex, String college, String major, String inyear)
    {
        try {
            return Result.success(studentMapper.getallstudent(studentid,name,sex,college,major,inyear));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Result.error(false);
        }

    }

    @Transactional
    public Result<Object> addstudentuser(String studentid,String password,String name,String sex,String major,String phone,String inyear) {
        try {
            String college = majorMapepr.getschoolbymajor(major);if(college.equals(null))return Result.error(false);
            student s = new student();
            s.setStudentId(Long.parseLong(studentid));s.setSex(sex);s.setCollege(college);s.setMajor(major);s.setPhone(Long.parseLong(phone));
            s.setInYear(Integer.parseInt(inyear));s.setName(name);
            String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
            s.setDate(time);
            users users=new users();
            users.setUsername(Long.parseLong(studentid));
            users.setPassword(password);users.setRole("student");
            if(studentMapper.insertSelective(s)&&usersmapper.insertSelective(users))
            {
                return Result.success(true);
            }
            else
            {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
                return Result.error(false);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Result.error(false);
        }
    }


    public Result<Object> deletestudentuser(long studentid) {
        try {
            if(studentMapper.deletebyid(studentid)) {
                usersmapper.deletebyusername(studentid);
                return Result.success(true);
            }
            else{
                return Result.error(false);
            }
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);
        }
    }

    public Result<Object> updateuser(String studentid, String phone, String major,String inyear, String sex) {
        try {
            student student=studentMapper.selectByPrimaryKey(Long.parseLong(studentid));
            if(student==null){return Result.error("不存在此id",false);}
            if(inyear!=null&&!"".equals(inyear)) {student.setInYear(Integer.parseInt(inyear));}
            if(phone!=null&&!"".equals(phone)){student.setPhone(Long.parseLong(phone));}
            if(sex!=null&&!"".equals(sex)){if(sex.equals("男")||sex.equals("女"))student.setSex(sex);}
            if(major!=null&&!"".equals(major))
            {
                String college=majorMapepr.getschoolbymajor(major);
                if(college==null||"".equals(college)){return Result.error("学院专业错误",false);}
                else
                {
                    student.setCollege(college);
                    student.setMajor(major);
                }
            }
            studentMapper.updateByPrimaryKeySelective(student);
            return Result.success(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            return Result.error("请检查参数",false);
        }
    }

    public Result<Object> updatepassword(String studentid, String oldpassword, String newpassword) {
        try {
            if(oldpassword==null||"".equals(oldpassword)||newpassword==null||"".equals(newpassword)){
                return  Result.error("参数为空");
            }
            student student=studentMapper.selectByPrimaryKey(Long.parseLong(studentid));
            if(student==null){return Result.error("不存咋此账号",false);}
            users user=usersmapper.getuserbystudentid(Long.parseLong(studentid));
            if(user==null){return Result.error(false);}
            if(user.getPassword().equals(oldpassword)){user.setPassword(newpassword);}
            usersmapper.updateByPrimaryKeySelective(user);
            return Result.success(true);
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);}
    }

    public Result<Object> updatestubyadmin(String studentid, String password, String name, String sex,
                                           String phone, String major, String inyear) {
        try {
            student student = studentMapper.selectByPrimaryKey(Long.parseLong(studentid));
            users user = usersmapper.getuserbystudentid(Long.parseLong(studentid));
            if (student == null) {
                return Result.error("不存在此id", false);
            }
            if (password != null||!"".equals(password)) {
                user.setPassword(password);
            }
            if (inyear != null||!"".equals(inyear)) {
                student.setInYear(Integer.parseInt(inyear));
            }
            if (phone != null||!"".equals(phone)) {
                student.setPhone(Long.parseLong(phone));
            }
            if (sex != null||!"".equals(sex)) {
                if (sex.equals("男") || sex.equals("女")) student.setSex(sex);
            }
            if (name != null||!"".equals(name)) {
                student.setName(name);
            }
            if (major != null||!"".equals(major)) {
                String college = majorMapepr.getschoolbymajor(major);
                if (college == null) {
                    return Result.error("学院专业错误", false);
                } else {
                    student.setCollege(college);
                    student.setMajor(major);
                }
            }
            studentMapper.updateByPrimaryKeySelective(student);
            usersmapper.updateByPrimaryKeySelective(user);
            return Result.success(true);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动事务回滚
            e.printStackTrace();return Result.error(false);}

    }


}
