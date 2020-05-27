package com.volunteer.controller;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteer.Result.Result;
import com.volunteer.service.publisherService;
import com.volunteer.service.studentService;
import com.volunteer.service.userService;
import io.swagger.annotations.Api;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("swagger2")
@RequestMapping("admin")
public class adminController {

    private final static Logger logger= LoggerFactory.getLogger(adminController.class);
    @Autowired(required = false)
    userService userService;
    @Autowired(required = false)
    studentService studentService;
    @Autowired(required = false)
    publisherService publisherService;

    //Result为封装的返回类型

    /**
     * 添加学生/义工账户，分为文件EXCEL添加或者表单添加
     * 如果传来的参数有文件，那么就用文件excel的内容，如果没有文件用扁担内容添加
     * excel处理用阿里开源的easyExcel文件
     * excel 的格式没一行要对应 学号、**详细看代码注释
     * @param file   excel文件
     * @param studentid 学号
     * @param password  密码
     * @param name       姓名
     * @param sex        性别
     * @param major      专业
     * @param phone      电话
     * @param inyear      入学年份
     * @return  json串，插入成功/失败个数
     */
    @PostMapping("addstudentuser")
    public Result<Object>addstudentuser(MultipartFile file, String studentid, String password, String name, String sex, String major, String phone, String inyear)
    {

        InputStream inputStream=null;
        int success=0;int fail=0;//插入成功，失败的个数
        if(file==null||file.isEmpty()){//文件为空用表单内容添加
        try {
            if (studentid==(null) || sex==(null) || major==(null) ||
                    password==(null)||phone==(null) || inyear==(null)||name==(null)) {
                return Result.error("请核实信息完整");
            } else {
                return studentService.addstudentuser(studentid,password,name, sex, major, phone, inyear);//调用service的方法插入
            }
        }
        catch (Exception e){logger.error(e.toString());return Result.error(false);}}
        else {//读取excel文件插入
            try {
                // 解析每行结果在listener中处理  判断是xls还是xlsx
                ExcelListener listener = new ExcelListener();
                inputStream = file.getInputStream();

                ExcelTypeEnum typeEnum = null;
                if (file.getOriginalFilename().contains("xlsx"))
                    typeEnum = ExcelTypeEnum.XLSX;
                else if (file.getOriginalFilename().contains("xls"))
                    typeEnum = ExcelTypeEnum.XLS;

                ExcelReader excelReader = new ExcelReader(inputStream, typeEnum, null, listener);

                excelReader.read(new Sheet(1, 1));//第一页的excel，第二行开始读
                //返回一个List对象包过一行的内容，其实是List<List>,因为插入的数据和user和student类不是完全匹配，只能用list自己取值构造对象
                List<Object> list = listener.getDatas();
                for (Object stuinformation : list){//这里是单个单个插入，后期如果优化可以使用sql 批量插入。但是数据不是很大影响不大
                     List<String>list1 = (List<String>) stuinformation;
                    try {
                        String stuid=list1.get(0);//学号
                        String uname=list1.get(1);//姓名
                        String upass=list1.get(2);//密码
                        String stusex=list1.get(3);//性别
                        String mj=list1.get(4);//专业
                        String ph=list1.get(5);//手机
                        String stuin=list1.get(6);//入学年份
                        try {
                            studentService.addstudentuser(stuid,upass,uname,stusex,mj,ph,stuin);
                            success++;
                        }
                        catch (Exception e)
                        {
                            logger.error(e.toString());e.printStackTrace();
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

    private static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 添加义工发布者，也是支持excel的，但是前端开发者使用js插件预览，将信息转成json字符串发送到后端
     * 后端需要解析json串，然后插入义工发布者
     * 使用jackson 进行解析json串。转成List<Map>
     * @param publist    前端给的json串
     * @return
     * @throws IOException
     */
    @PostMapping("addpublisher")
    public Result addstudentuser(String publist) throws IOException {
        //jackson 语法
        JavaType jt = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, HashMap.class);
        List<Map> publist1 = MAPPER.readValue(publist, jt);
        int suncess=0;
        if (publist1==null){return Result.error(false);}
        for(int i=0;i<publist1.size();i++)
        {
            Map<String,String> pub=publist1.get(i);
            String publisherid=String.valueOf(pub.get("publisherid"));
            String password=String.valueOf(pub.get("password"));
            String name=pub.get("name");
            String phone=String.valueOf(pub.get("phone"));
            String location=pub.get("location");
            try {//不能为"" 如果为null数据库会自动回滚，所以不需要考虑null
                if(publisherid.equals("")||name.equals("")||phone.equals("")||location.equals("")||password.equals(""))
                {
                   continue;
                }
                else
                {
                     publisherService.addpublisher(publisherid,password,name,phone,location);
                     suncess++;
                }
            }catch (Exception e)
            {e.printStackTrace();}

        }
        return Result.success("成功插入:"+suncess+"条，失败："+(publist1.size()-suncess)+"条");

    }
    @PostMapping("deletestudentuser")//删除学生
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

    /**
     * 更新学生的信息，有些可能为空，所以再service更新的时候要判断是否为空，如果为空不能更新为空或者null
     * @param studentid  略
     * @param password
     * @param name
     * @param sex
     * @param phone
     * @param major
     * @param inyear
     * @return
     */
    @PostMapping("updatestudent")//更新学生信息
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
