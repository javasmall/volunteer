package com.volunteer.pojo;

public class workAttend {
    private Integer id;

    private String studentName;

    private Long studentId;

    private Integer workId;

    private String workName;

    private Boolean isfinished;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }


    public Boolean getIsfinished() {
        return isfinished;
    }

    public void setIsfinished(Boolean isfinished) {
        this.isfinished = isfinished;
    }
}