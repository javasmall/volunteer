package com.volunteer.pojo;

import java.util.Date;

public class workInformation {
    private Integer id;

    private String name;


    private Long publisherId;

    private String publishTime;

    private String workCampus;

    private String workDepartment;

    private String workAddr;

    private String workTip;

    private double workHour;

    private Integer needNum;

    private Integer attendNum;

    private String startTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }





    public String getWorkCampus() {
        return workCampus;
    }

    public void setWorkCampus(String workCampus) {
        this.workCampus = workCampus == null ? null : workCampus.trim();
    }

    public String getWorkDepartment() {
        return workDepartment;
    }

    public void setWorkDepartment(String workDepartment) {
        this.workDepartment = workDepartment == null ? null : workDepartment.trim();
    }

    public String getWorkAddr() {
        return workAddr;
    }

    public void setWorkAddr(String workAddr) {
        this.workAddr = workAddr == null ? null : workAddr.trim();
    }

    public String getWorkTip() {
        return workTip;
    }

    public void setWorkTip(String workTip) {
        this.workTip = workTip == null ? null : workTip.trim();
    }



    public Integer getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
    }

    public Integer getAttendNum() {
        return attendNum;
    }

    public void setAttendNum(Integer attendNum) {
        this.attendNum = attendNum;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Double getWorkHour() {
        return workHour;
    }

    public void setWorkHour(Double workHour) {
        this.workHour = workHour;
    }
}