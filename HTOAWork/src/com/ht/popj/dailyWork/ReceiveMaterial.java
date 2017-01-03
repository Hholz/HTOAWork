package com.ht.popj.dailyWork;

import java.util.Date;

public class ReceiveMaterial {
    private Integer id;

    private Integer materialtypeid;
    
    private String materialname;

    private String unit;
    
    private String empid;
    
    private String materialtypename;
    
    private String empname;

    private Integer counts;

    private String applymaterialRemark;

    private Integer approvalstatus;

    private Integer materialid;

    private Integer flowid;

    private Integer upset;

    private String applynum;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaterialtypeid() {
        return materialtypeid;
    }

    public void setMaterialtypeid(Integer materialtypeid) {
        this.materialtypeid = materialtypeid;
    }

    public String getMaterialname() {
		return materialname;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public String getMaterialtypename() {
		return materialtypename;
	}

	public void setMaterialtypename(String materialtypename) {
		this.materialtypename = materialtypename;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getApplymaterialRemark() {
        return applymaterialRemark;
    }

    public void setApplymaterialRemark(String applymaterialRemark) {
        this.applymaterialRemark = applymaterialRemark == null ? null : applymaterialRemark.trim();
    }

    public Integer getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(Integer approvalstatus) {
        this.approvalstatus = approvalstatus;
    }


    public Integer getMaterialid() {
		return materialid;
	}

	public void setMaterialid(Integer materialid) {
		this.materialid = materialid;
	}

	public Integer getFlowid() {
        return flowid;
    }

    public void setFlowid(Integer flowid) {
        this.flowid = flowid;
    }

    public Integer getUpset() {
        return upset;
    }

    public void setUpset(Integer upset) {
        this.upset = upset;
    }

    public String getApplynum() {
        return applynum;
    }

    public void setApplynum(String applynum) {
        this.applynum = applynum == null ? null : applynum.trim();
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