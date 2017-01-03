package com.ht.popj.dailyWork;

import java.util.Date;

public class Attendancecount {
    private Integer id;

    private String empid;

    private Integer scikcount;

    private Integer casualcount;

    private Integer dutycount;

    private Integer missworkcount;

    private Integer overworkcount;

    private Integer latecount;

    private Date createtime;

    private Date updatetime;

    private Integer status;
    
    private String time;

    private String remark;
    
    private String empname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public Integer getScikcount() {
        return scikcount;
    }

    public void setScikcount(Integer scikcount) {
        this.scikcount = scikcount;
    }

    public Integer getCasualcount() {
        return casualcount;
    }

    public void setCasualcount(Integer casualcount) {
        this.casualcount = casualcount;
    }

    public Integer getDutycount() {
        return dutycount;
    }

    public void setDutycount(Integer dutycount) {
        this.dutycount = dutycount;
    }

    public Integer getMissworkcount() {
        return missworkcount;
    }

    public void setMissworkcount(Integer missworkcount) {
        this.missworkcount = missworkcount;
    }

    public Integer getOverworkcount() {
        return overworkcount;
    }

    public void setOverworkcount(Integer overworkcount) {
        this.overworkcount = overworkcount;
    }

    public Integer getLatecount() {
        return latecount;
    }

    public void setLatecount(Integer latecount) {
        this.latecount = latecount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
    
    
}