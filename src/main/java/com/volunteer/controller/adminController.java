package com.volunteer.controller;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;


import com.volunteer.Result.Result;
import com.volunteer.pojo.student;
import com.volunteer.service.publisherService;
import com.volunteer.service.studentService;
import com.volunteer.service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class adminController {

    private final static Logger logger= LoggerFactory.getLogger(adminController.class);
    @Autowired(required = false)
    userService userService;
    @Autowired(required = false)
    studentService studentService;
    @Autowired(required = false)
    publisherService publisherService;
    @PostMapping("addstudentuser")
    public Result<Object>addstudentuser(MultipartFile file, String studentid, String password, String name, String sex, String major, String phone, String inyear)
    {

        InputStream inputStream=null;
        int success=0;int fail=0;
        if(file==null){
        try {
            if (studentid==(null) || sex==(null) || major==(null) ||
                    password==(null)||phone==(null) || inyear==(null)||name==(null)) {
                return Result.error("请核实信息完整");
            } else {
                return studentService.addstudentuser(studentid,password,name, sex, major, phone, inyear);
            }
        }
        catch (Exception e){e.printStackTrace();return Result.error(false);}}
        else {
            try {
                // 解析每行结果在listener中处理
                ExcelListener listener = new ExcelListener();
                inputStream = file.getInputStream();

                ExcelTypeEnum typeEnum = null;
                if (file.getOriginalFilename().contains("xlsx"))
                    typeEnum = ExcelTypeEnum.XLSX;
                else if (file.getOriginalFilename().contains("xls"))
                    typeEnum = ExcelTypeEnum.XLS;

                ExcelReader excelReader = new ExcelReader(inputStream, typeEnum, null, listener);

                excelReader.read(new Sheet(1, 1));
                List<Object> list = listener.getDatas();
                for (Object stuinformation : list){
                     List<String>list1 = (List<String>) stuinformation;
                    try {
                        String stuid=list1.get(0);
                        String uname=list1.get(1);
                        String upass=list1.get(2);
                        String stusex=list1.get(3);
                        String mj=list1.get(4);
                        String ph=list1.get(5);
                        String stuin=list1.get(6);
                        try {
                            studentService.addstudentuser(stuid,upass,uname,stusex,mj,ph,stuin);
                            success++;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            fail++;
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();fail++;
                    }
                    //logger.info(student.toString());
                }
                return Result.success("插入成功："+success+"组,插入失败："+fail);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error(false);

            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

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

    /* 解析监听器，
     * 每解析一行会回调invoke()方法。
     * 整个excel解析结束会执行doAfterAllAnalysed()方法
     *
     * 下面只是我写的一个样例而已，可以根据自己的逻辑修改该类。
     * @author jipengfei
     * @date 2017/03/14
     */
    public static class ExcelListener extends AnalysisEventListener {

        //自定义用于暂时存储data。
        //可以通过实例获取该值
        private List<Object> datas = new ArrayList<Object>();
        public void invoke(Object object, AnalysisContext context) {
//            System.out.println("当前行："+context.getCurrentRowNum());
//            System.out.println(object.toString());
            datas.add(object);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
            doSomething(object);//根据自己业务做处理
        }

        private void doSomething(Object object) {
            //1、入库调用接口
        }
        public void doAfterAllAnalysed(AnalysisContext context) {
            // datas.clear();//解析结束销毁不用的资源
        }
        public List<Object> getDatas() {
            return datas;
        }
        public void setDatas(List<Object> datas) {
            this.datas = datas;
        }
    }


}
