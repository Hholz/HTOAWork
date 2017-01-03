package com.ht.popj.dailyWork;

import java.util.Date;

public class Baoxiaotype {
    private Integer id;

    private String baoxiaotypename;

    private String baoxiaotypeRemark;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaoxiaotypename() {
        return baoxiaotypename;
    }

    public void setBaoxiaotypename(String baoxiaotypename) {
        this.baoxiaotypename = baoxiaotypename == null ? null : baoxiaotypename.trim();
    }

    public String getBaoxiaotypeRemark() {
        return baoxiaotypeRemark;
    }

    public void setBaoxiaotypeRemark(String baoxiaotypeRemark) {
        this.baoxiaotypeRemark = baoxiaotypeRemark == null ? null : baoxiaotypeRemark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}