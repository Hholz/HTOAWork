package com.ht.popj.dailyWork;

import java.util.Date;

public class AttenstatisM {
    private Integer id;

    private Integer finger;

    private String empname;

    private Integer depid;

    private String workday;
    
    private String endday;

    private Integer attenstatus;

    private Integer late;

    private Float absent;

    private Float leave;

    private String updatetime;

    private String remark;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFinger() {
        return finger;
    }

    public void setFinger(Integer finger) {
        this.finger = finger;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }
  
    public Integer getAttenstatus() {
        return attenstatus;
    }

    public void setAttenstatus(Integer attenstatus) {
        this.attenstatus = attenstatus;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public Float getAbsent() {
        return absent;
    }

    public void setAbsent(Float absent) {
        this.absent = absent;
    }

    public Float getLeave() {
        return leave;
    }

    public void setLeave(Float leave) {
        this.leave = leave;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getWorkday() {
		return workday;
	}

	public void setWorkday(String workday) {
		this.workday = workday;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getEndday() {
		return endday;
	}

	public void setEndday(String endday) {
		this.endday = endday;
	}
    
    
}