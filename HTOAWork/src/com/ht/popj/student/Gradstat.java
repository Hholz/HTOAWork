package com.ht.popj.student;

public class Gradstat {
    private Integer id;

    private String gradstatus;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradstatus() {
        return gradstatus;
    }

    public void setGradstatus(String gradstatus) {
        this.gradstatus = gradstatus == null ? null : gradstatus.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}