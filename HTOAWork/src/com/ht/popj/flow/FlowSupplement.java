package com.ht.popj.flow;


import com.ht.popj.dailyWork.Emp;

public class FlowSupplement {
    private Integer id;

    private String empid;
    
    private Emp emp;

    private String sutime;

    private String suremark;

    private String createtime;

    private String updatetime;

    private Integer status;

    private String remark;
    
    private Integer approvalstatus;
    
    private Integer flowmodeltypeid;
    
    private String applyman;
    
    public Integer getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(Integer approvalstatus) {
		this.approvalstatus = approvalstatus;
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

    public String getSutime() {
		return sutime;
	}

	public void setSutime(String sutime) {
		this.sutime = sutime;
	}

	public String getSuremark() {
        return suremark;
    }

    public void setSuremark(String suremark) {
        this.suremark = suremark == null ? null : suremark.trim();
    }

    public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
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

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public Integer getFlowmodeltypeid() {
		return flowmodeltypeid;
	}

	public void setFlowmodeltypeid(Integer flowmodeltypeid) {
		this.flowmodeltypeid = flowmodeltypeid;
	}

	public String getApplyman() {
		return applyman;
	}

	public void setApplyman(String applyman) {
		this.applyman = applyman;
	}
    
    
}