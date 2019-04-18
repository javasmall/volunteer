package com.volunteer;


import com.volunteer.Result.Result;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VolunteerApplicationTests {

   private  static final Logger logger= LoggerFactory.getLogger(VolunteerApplicationTests.class);
    @Autowired(required = false)
    com.volunteer.service.workAttendService workAttendService;
    @Test
    public void contextLoads()  {

    }
    @Test
    public void test1()
    {
        String a="162210702234";
        Result result =workAttendService.getworktimethisterm(Long.parseLong(a));
        logger.info(result.getData().toString());

    }
    @Test
    public void test2() throws ParseException {
        String a="162210702234";
        String b="2019-03-22";String c="2019-04-24";
        Result result=workAttendService.getworktimeselect(a,b,c);
        logger.info(result.getData().toString());
        System.out.println(result.getData().toString());
    }
    @Test
    public void test3()
    {

    }

}
