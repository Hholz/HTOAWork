package com.ht.popj.dailyWork;

import java.util.Date;

public class Holiday {
    private Integer id;

    private Integer holidaytypeid;
    
    private String holidaytypename;

    private Integer flowmodelid;
    
    private Integer depid;

    private String empid;
    
    private String empname;

    private Integer holidayday;

    private String starttime;

    private String endtime;

    private String holidayRemark;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private String appnum;

    private Integer approvalstatus;

    private Integer upset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHolidaytypeid() {
        return holidaytypeid;
    }

    public void setHolidaytypeid(Integer holidaytypeid) {
        this.holidaytypeid = holidaytypeid;
    }

    public Integer getFlowmodelid() {
        return flowmodelid;
    }

    public void setFlowmodelid(Integer flowmodelid) {
        this.flowmodelid = flowmodelid;
    }

    public Integer getDepid() {
		return depid;
	}

	public void setDepid(Integer depid) {
		this.depid = depid;
	}

	public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public String getAppnum() {
		return appnum;
	}

	public void setAppnum(String appnum) {
		this.appnum = appnum;
	}

	public String getHolidaytypename() {
		return holidaytypename;
	}

	public void setHolidaytypename(String holidaytypename) {
		this.holidaytypename = holidaytypename;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Integer getHolidayday() {
        return holidayday;
    }

    public void setHolidayday(Integer holidayday) {
        this.holidayday = holidayday;
    }


    public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getHolidayRemark() {
        return holidayRemark;
    }

    public void setHolidayRemark(String holidayRemark) {
        this.holidayRemark = holidayRemark == null ? null : holidayRemark.trim();
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

    public Integer getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(Integer approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public Integer getUpset() {
        return upset;
    }

    public void setUpset(Integer upset) {
        this.upset = upset;
    }
}