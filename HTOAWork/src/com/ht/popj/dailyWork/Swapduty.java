package com.ht.popj.dailyWork;

public class Swapduty {
    private Integer id;

    private String dutyid;
    
    private String empname;

    private String weeks1;

    private String weekends1;

    private String weeks2;

    private String weekends2;

    private String empid;

    private String applyreason;

    private String applytime;

    private Integer applystatus;
    
    private Integer flowmodelid;
    
    private String appnum;
    
    private Integer approvalstatus;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private Emp emp;
    
    private Emp emp1;

    public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public Emp getEmp1() {
		return emp1;
	}

	public void setEmp1(Emp emp1) {
		this.emp1 = emp1;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDutyid() {
        return dutyid;
    }

    public void setDutyid(String dutyid) {
        this.dutyid = dutyid;
    }

    public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getWeeks1() {
        return weeks1;
    }

    public void setWeeks1(String weeks1) {
        this.weeks1 = weeks1 == null ? null : weeks1.trim();
    }

    public String getWeekends1() {
        return weekends1;
    }

    public void setWeekends1(String weekends1) {
        this.weekends1 = weekends1 == null ? null : weekends1.trim();
    }

    public String getWeeks2() {
        return weeks2;
    }

    public void setWeeks2(String weeks2) {
        this.weeks2 = weeks2 == null ? null : weeks2.trim();
    }

    public String getWeekends2() {
        return weekends2;
    }

    public void setWeekends2(String weekends2) {
        this.weekends2 = weekends2 == null ? null : weekends2.trim();
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public String getApplyreason() {
        return applyreason;
    }

    public void setApplyreason(String applyreason) {
        this.applyreason = applyreason == null ? null : applyreason.trim();
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public Integer getApplystatus() {
        return applystatus;
    }

    public void setApplystatus(Integer applystatus) {
        this.applystatus = applystatus;
    }

    public Integer getFlowmodelid() {
		return flowmodelid;
	}

	public void setFlowmodelid(Integer flowmodelid) {
		this.flowmodelid = flowmodelid;
	}

	public String getAppnum() {
		return appnum;
	}

	public void setAppnum(String appnum) {
		this.appnum = appnum;
	}

	public Integer getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(Integer approvalstatus) {
		this.approvalstatus = approvalstatus;
	}

	public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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