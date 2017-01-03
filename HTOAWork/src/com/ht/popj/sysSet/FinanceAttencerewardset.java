package com.ht.popj.sysSet;

import java.util.Date;

public class FinanceAttencerewardset {
    private Integer id;

    private Float latesalary;

    private Float overtimesalary;

    private String leavesalary;

    private String withoutleavesalary;

    private String dutysalary;

    private String sickleavesalary;

    private String createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLatesalary() {
        return latesalary;
    }

    public void setLatesalary(Float latesalary) {
        this.latesalary = latesalary;
    }

    public Float getOvertimesalary() {
        return overtimesalary;
    }

    public void setOvertimesalary(Float overtimesalary) {
        this.overtimesalary = overtimesalary;
    }

    public String getLeavesalary() {
        return leavesalary;
    }

    public void setLeavesalary(String leavesalary) {
        this.leavesalary = leavesalary == null ? null : leavesalary.trim();
    }

    public String getWithoutleavesalary() {
        return withoutleavesalary;
    }

    public void setWithoutleavesalary(String withoutleavesalary) {
        this.withoutleavesalary = withoutleavesalary == null ? null : withoutleavesalary.trim();
    }

    public String getDutysalary() {
        return dutysalary;
    }

    public void setDutysalary(String dutysalary) {
        this.dutysalary = dutysalary == null ? null : dutysalary.trim();
    }

    public String getSickleavesalary() {
        return sickleavesalary;
    }

    public void setSickleavesalary(String sickleavesalary) {
        this.sickleavesalary = sickleavesalary == null ? null : sickleavesalary.trim();
    }

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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