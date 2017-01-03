package com.ht.popj.finance;

import java.util.Date;

public class FinanceSalaryset {
    private Integer id;

    private String empid;
    
    private String empname;
    
    private Integer depid;
    
    private String[] stypeid;
    
    private String depname;
    
    private String salarytypename;

    private Integer salarytypeid;

    private Float money;
    
    private Float downmoney;
    
    private Float upmoney;

    private String salarysetRemark;

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

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public Integer getSalarytypeid() {
        return salarytypeid;
    }

    public void setSalarytypeid(Integer salarytypeid) {
        this.salarytypeid = salarytypeid;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

	public String[] getStypeid() {
		return stypeid;
	}

	public void setStypeid(String[] stypeid) {
		this.stypeid = stypeid;
	}

	public String getSalarysetRemark() {
        return salarysetRemark;
    }

    public void setSalarysetRemark(String salarysetRemark) {
        this.salarysetRemark = salarysetRemark == null ? null : salarysetRemark.trim();
    }

    public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Integer getDepid() {
		return depid;
	}

	public void setDepid(Integer depid) {
		this.depid = depid;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getSalarytypename() {
		return salarytypename;
	}

	public void setSalarytypename(String salarytypename) {
		this.salarytypename = salarytypename;
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

    public Float getDownmoney() {
		return downmoney;
	}

	public void setDownmoney(Float downmoney) {
		this.downmoney = downmoney;
	}

	public Float getUpmoney() {
		return upmoney;
	}

	public void setUpmoney(Float upmoney) {
		this.upmoney = upmoney;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}