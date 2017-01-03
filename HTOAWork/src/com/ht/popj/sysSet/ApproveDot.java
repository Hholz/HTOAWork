package com.ht.popj.sysSet;

import java.util.Date;

public class ApproveDot {
    private Integer id;

    private Integer flowid;
    
    private String flowname;
    
    private String applople;
    
    private String depname;
    
    private String dotname;

    private Integer lastdot;

    private String lastdotname;
    private String approveman;

    private Integer approvedep;

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

    public Integer getFlowid() {
        return flowid;
    }

    public void setFlowid(Integer flowid) {
        this.flowid = flowid;
    }

    public String getDotname() {
        return dotname;
    }

    public void setDotname(String dotname) {
        this.dotname = dotname == null ? null : dotname.trim();
    }

    public Integer getLastdot() {
        return lastdot;
    }

    public void setLastdot(Integer lastdot) {
        this.lastdot = lastdot;
    }

    public String getLastdotname() {
		return lastdotname;
	}

	public void setLastdotname(String lastdotname) {
		this.lastdotname = lastdotname;
	}

	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	public String getApplople() {
		return applople;
	}

	public void setApplople(String applople) {
		this.applople = applople;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}


    public String getApproveman() {
		return approveman;
	}

	public void setApproveman(String approveman) {
		this.approveman = approveman;
	}

	public Integer getApprovedep() {
        return approvedep;
    }

    public void setApprovedep(Integer approvedep) {
        this.approvedep = approvedep;
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