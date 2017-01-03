package com.ht.popj.dailyWork;

public class Duty {
    private Integer id;

    private String dutyid;

    private String weeks;

    private String weekends;

    private String dutytime;

    private Integer dutystatus;

    private String reason;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private Emp emp;

    public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
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
        this.dutyid = dutyid == null ? null : dutyid.trim();
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks == null ? null : weeks.trim();
    }

    public String getWeekends() {
        return weekends;
    }

    public void setWeekends(String weekends) {
        this.weekends = weekends == null ? null : weekends.trim();
    }

    public String getDutytime() {
        return dutytime;
    }

    public void setDutytime(String dutytime) {
        this.dutytime = dutytime;
    }

    public Integer getDutystatus() {
        return dutystatus;
    }

    public void setDutystatus(Integer dutystatus) {
        this.dutystatus = dutystatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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