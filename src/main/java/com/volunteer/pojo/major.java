package com.volunteer.pojo;

public class major {
    private Integer id;

    private String college;

    private String major;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColloge() {
        return college;
    }

    public void setColloge(String colloge) {
        this.college = colloge == null ? null : colloge.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }
}