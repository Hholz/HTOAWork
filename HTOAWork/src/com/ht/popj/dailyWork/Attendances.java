package com.ht.popj.dailyWork;

import java.util.Date;

import com.ht.popj.sysSet.FinanceAttenceset;

public class Attendances {
    private Integer id;

    private String empid;

    private String workday;

    private String flag;

    private String createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private String attentime;
    
    private Emp emp;
    
    private FinanceAttenceset financeAtten;
    

    public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public FinanceAttenceset getFinanceAtten() {
		return financeAtten;
	}

	public void setFinanceAtten(FinanceAttenceset financeAtten) {
		this.financeAtten = financeAtten;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public String getWorkday() {
		return workday;
	}

	public void setWorkday(String workday) {
		this.workday = workday;
	}

	public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
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

    public String getAttentime() {
        return attentime;
    }

    public void setAttentime(String attentime) {
        this.attentime = attentime == null ? null : attentime.trim();
    }
}