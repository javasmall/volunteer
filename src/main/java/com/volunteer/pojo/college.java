package com.volunteer.pojo;

public class college {
    private Integer id;

    private String college;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String collegege) {
        this.college = college == null ? null : college.trim();
    }
}