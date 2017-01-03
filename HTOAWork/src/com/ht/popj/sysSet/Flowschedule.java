package com.ht.popj.sysSet;

import java.util.Date;

public class Flowschedule {
    private Integer id;

    private String applynum;
    
    private String empid;

    private Integer flowtype;

    private Integer flowdot;

    private Integer flowstatus;

    private Date createtime;

    private Date updatetime;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplynum() {
        return applynum;
    }

    public void setApplynum(String applynum) {
        this.applynum = applynum == null ? null : applynum.trim();
    }

    public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public Integer getFlowtype() {
        return flowtype;
    }

    public void setFlowtype(Integer flowtype) {
        this.flowtype = flowtype;
    }

    public Integer getFlowdot() {
        return flowdot;
    }

    public void setFlowdot(Integer flowdot) {
        this.flowdot = flowdot;
    }

    public Integer getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(Integer flowstatus) {
        this.flowstatus = flowstatus;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
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
}