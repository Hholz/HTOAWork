package com.ht.popj.dailyWork;

import java.math.BigDecimal;
import java.util.Date;

public class ApplyMaterial {
    private Integer id;

    private Integer materialtypeid;
    
    private String materiatypename;

    private String empid;
    
    private String empname;

    private String unit;

    private BigDecimal price;

    private String style;

    private Integer counts;
    
    private Integer flowid;
    
    private Integer finaceid;
    
    private String applynum;

    private String applymaterialRemark;
    
    private String materialname;

    private String createTime;

    private String updateTime;
    
    private Integer upset;

    private Integer status;

    private String remark;

    private Integer approvalstatus;

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
		this.applynum = applynum;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public Integer getFlowid() {
		return flowid;
	}

	public void setFlowid(Integer flowid) {
		this.flowid = flowid;
	}

	public Integer getFinaceid() {
		return finaceid;
	}

	public void setFinaceid(Integer finaceid) {
		this.finaceid = finaceid;
	}

	public String getMateriatypename() {
		return materiatypename;
	}

	public void setMateriatypename(String materiatypename) {
		this.materiatypename = materiatypename;
	}


	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style == null ? null : style.trim();
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

}