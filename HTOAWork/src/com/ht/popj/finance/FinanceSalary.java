package com.ht.popj.finance;

import java.util.Date;

public class FinanceSalary {
    private Integer id;

    private String empid;

    private String time;

    private Float money;

    private Integer financeStatus;

    private String salaryRemark;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private String typename;
    
    private String empname;
    
    private String depname;
    
    private Integer depid;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(Integer financeStatus) {
        this.financeStatus = financeStatus;
    }

    public String getSalaryRemark() {
        return salaryRemark;
    }

    public void setSalaryRemark(String salaryRemark) {
        this.salaryRemark = salaryRemark == null ? null : salaryRemark.trim();
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

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public int getDepid() {
		return depid;
	}

	public void setDepid(int depid) {
		this.depid = depid;
	}

	@Override
	public String toString() {
		return "FinanceSalary [id=" + id + ", empid=" + empid + ", time=" + time + ", money=" + money
				+ ", financeStatus=" + financeStatus + ", salaryRemark=" + salaryRemark + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + ", remark=" + remark + ", typename=" + typename
				+ ", empname=" + empname + ", depname=" + depname + ", depid=" + depid + "]";
	}
	
}