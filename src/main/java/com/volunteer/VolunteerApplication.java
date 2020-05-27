package com.volunteer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.volunteer.config")
@SpringBootApplication
public class VolunteerApplication  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VolunteerApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(VolunteerApplication.class, args);
    }

}
