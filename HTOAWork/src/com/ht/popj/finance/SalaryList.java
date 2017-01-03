package com.ht.popj.finance;

import com.ht.popj.dailyWork.Dep;

public class SalaryList {
    private Integer id;

    private String empname;

    private Integer depid;

    private Integer basicid;

    private Float jobsalary;

    private Float deductmoney;

    private Float prizemoney;

    private Float shouldsalary;

    private Float factsalary;

    private Float taxsalary;

    private String salaryRemark;

    private String createTime;

    private String updateTime;

    private Integer status;

    private Dep dep;
    
    private BasicSalary basic;
    
    public BasicSalary getBasic() {
		return basic;
	}

	public void setBasic(BasicSalary basic) {
		this.basic = basic;
	}

	public Dep getDep() {
		return dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

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
        this.empname = empname == null ? null : empname.trim();
    }

    public Integer getDepid() {
        return depid;
    }

    public Integer getBasicid() {
		return basicid;
	}

	public void setBasicid(Integer basicid) {
		this.basicid = basicid;
	}

	public void setDepid(Integer depid) {
		this.depid = depid;
	}

    public Float getJobsalary() {
        return jobsalary;
    }

    public void setJobsalary(Float jobsalary) {
        this.jobsalary = jobsalary;
    }

    public Float getDeductmoney() {
        return deductmoney;
    }

    public void setDeductmoney(Float deductmoney) {
        this.deductmoney = deductmoney;
    }

    public Float getPrizemoney() {
        return prizemoney;
    }

    public void setPrizemoney(Float prizemoney) {
        this.prizemoney = prizemoney;
    }

    public Float getShouldsalary() {
        return shouldsalary;
    }

    public void setShouldsalary(Float shouldsalary) {
        this.shouldsalary = shouldsalary;
    }

    public Float getFactsalary() {
        return factsalary;
    }

    public void setFactsalary(Float factsalary) {
        this.factsalary = factsalary;
    }

    public Float getTaxsalary() {
        return taxsalary;
    }

    public void setTaxsalary(Float taxsalary) {
        this.taxsalary = taxsalary;
    }

    public String getSalaryRemark() {
        return salaryRemark;
    }

    public void setSalaryRemark(String salaryRemark) {
        this.salaryRemark = salaryRemark == null ? null : salaryRemark.trim();
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