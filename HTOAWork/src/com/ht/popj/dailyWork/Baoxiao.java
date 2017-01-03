package com.ht.popj.dailyWork;

import java.math.BigDecimal;

public class Baoxiao {
    private Integer id;

    private Integer baoxiaotypeid;

    private Integer flowmodeltypeid;

    private Integer flowid;

    private String empid;

    private BigDecimal price;

    private Integer flowstatus;

    private String baoxiaoRemark;

    private String createTime;

    private String updateTime;

    private Byte status;

    private String remark;
    
    private String empname;
    
    private String baoxiaotypename;
    
    private String applynum;
    
    private String approverman;
    
    private int financeid; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBaoxiaotypeid() {
        return baoxiaotypeid;
    }

    public void setBaoxiaotypeid(Integer baoxiaotypeid) {
        this.baoxiaotypeid = baoxiaotypeid;
    }

    public Integer getFlowmodeltypeid() {
        return flowmodeltypeid;
    }

    public void setFlowmodeltypeid(Integer flowmodeltypeid) {
        this.flowmodeltypeid = flowmodeltypeid;
    }

    public Integer getFlowid() {
		return flowid;
	}

	public void setFlowid(Integer flowid) {
		this.flowid = flowid;
	}

	public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(Integer flowstatus) {
        this.flowstatus = flowstatus;
    }

    public String getBaoxiaoRemark() {
        return baoxiaoRemark;
    }

    public void setBaoxiaoRemark(String baoxiaoRemark) {
        this.baoxiaoRemark = baoxiaoRemark == null ? null : baoxiaoRemark.trim();
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getBaoxiaotypename() {
		return baoxiaotypename;
	}

	public void setBaoxiaotypename(String baoxiaotypename) {
		this.baoxiaotypename = baoxiaotypename;
	}

	public String getApplynum() {
		return applynum;
	}

	public void setApplynum(String applynum) {
		this.applynum = applynum;
	}

	public String getApproverman() {
		return approverman;
	}

	public void setApproverman(String approverman) {
		this.approverman = approverman;
	}

	public int getFinanceid() {
		return financeid;
	}

	public void setFinanceid(int financeid) {
		this.financeid = financeid;
	}

	@Override
	public String toString() {
		return "Baoxiao [id=" + id + ", baoxiaotypeid=" + baoxiaotypeid + ", flowmodeltypeid=" + flowmodeltypeid
				+ ", flowid=" + flowid + ", empid=" + empid + ", price=" + price + ", flowstatus=" + flowstatus
				+ ", baoxiaoRemark=" + baoxiaoRemark + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", status=" + status + ", remark=" + remark + ", empname=" + empname + ", baoxiaotypename="
				+ baoxiaotypename + ", applynum=" + applynum + ", approverman=" + approverman + ", financeid="
				+ financeid + "]";
	}
    
}