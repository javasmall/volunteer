package com.volunteer.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class checkService {
    Logger logger= LoggerFactory.getLogger(checkService.class);
    public boolean checkwork_time(double time)
    {
        logger.error(time+"");
        if(time<0.5||time>2){return false;}
        else if((time/(double)(0.5))%1!=0){return false;}
        else return true;
    }
}
