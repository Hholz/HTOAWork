package com.ht.popj.finance;

import com.ht.popj.dailyWork.Dep;

public class BasicSalary {
    private Integer id;

    private Integer depid;

    private String empname;

    private Float basicsalary;

    private String createTime;

    private String updateTime;

    private Integer status;

    private Dep dep;
    
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

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public Float getBasicsalary() {
        return basicsalary;
    }

    public void setBasicsalary(Float basicsalary) {
        this.basicsalary = basicsalary;
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