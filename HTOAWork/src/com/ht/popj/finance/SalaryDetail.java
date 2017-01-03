package com.ht.popj.finance;

import java.util.Date;

import com.ht.popj.sysSet.FinanceSalarytypese;

public class SalaryDetail {
    private Integer id;

    private Integer salaryId;

    private Integer salTypeid;
    
    private String empid;

    private Integer depid;

    private Float money;

    private String remark;

    private String createTime;

    private String updateTime;

    private Integer status;

    private FinanceSalarytypese type;
    
    public FinanceSalarytypese getType() {
		return type;
	}

	public void setType(FinanceSalarytypese type) {
		this.type = type;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }

    public Integer getSalTypeid() {
		return salTypeid;
	}

	public void setSalTypeid(Integer salTypeid) {
		this.salTypeid = salTypeid;
	}

	public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}