package com.ht.popj.dailyWork;

import java.util.Date;

public class HolidayType {
    private Integer id;

    private String holidaytypename;

    private String holidaytypeRemark;

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

    public String getHolidaytypename() {
        return holidaytypename;
    }

    public void setHolidaytypename(String holidaytypename) {
        this.holidaytypename = holidaytypename == null ? null : holidaytypename.trim();
    }

    public String getHolidaytypeRemark() {
        return holidaytypeRemark;
    }

    public void setHolidaytypeRemark(String holidaytypeRemark) {
        this.holidaytypeRemark = holidaytypeRemark == null ? null : holidaytypeRemark.trim();
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