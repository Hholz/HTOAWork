package com.ht.popj.login;

import java.util.Date;

public class ShiroAuthType {
    private Integer id;

    private String satName;

    private String satDesc;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSatName() {
        return satName;
    }

    public void setSatName(String satName) {
        this.satName = satName == null ? null : satName.trim();
    }

    public String getSatDesc() {
        return satDesc;
    }

    public void setSatDesc(String satDesc) {
        this.satDesc = satDesc == null ? null : satDesc.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}