package com.ht.popj.flow;

import java.util.Date;

public class FlowSwaparrange {
    private Integer id;

    private Integer flowmodeltypeid;

    private String changeOne;

    private Integer changeTwo;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private Integer status;

    private Integer upset;
    
    private String applyman;
    
    private Integer approvalstatus;
    
    private String changeTwoMan;
    
    private String weekends;
    
    

    public String getWeekends() {
		return weekends;
	}

	public void setWeekends(String weekends) {
		this.weekends = weekends;
	}

	public String getChangeTwoMan() {
		return changeTwoMan;
	}

	public void setChangeTwoMan(String changeTwoMan) {
		this.changeTwoMan = changeTwoMan;
	}

	public Integer getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(Integer approvalstatus) {
		this.approvalstatus = approvalstatus;
	}

	public String getApplyman() {
		return applyman;
	}

	public void setApplyman(String applyman) {
		this.applyman = applyman;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowmodeltypeid() {
        return flowmodeltypeid;
    }

    public void setFlowmodeltypeid(Integer flowmodeltypeid) {
        this.flowmodeltypeid = flowmodeltypeid;
    }

    public String getChangeOne() {
        return changeOne;
    }

    public void setChangeOne(String changeOne) {
        this.changeOne = changeOne == null ? null : changeOne.trim();
    }

    public Integer getChangeTwo() {
        return changeTwo;
    }

    public void setChangeTwo(Integer changeTwo) {
        this.changeTwo = changeTwo;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUpset() {
        return upset;
    }

    public void setUpset(Integer upset) {
        this.upset = upset;
    }

	@Override
	public String toString() {
		return "FlowSwaparrange [id=" + id + ", flowmodeltypeid=" + flowmodeltypeid + ", changeOne=" + changeOne
				+ ", changeTwo=" + changeTwo + ", createTime=" + createTime + ", updateTime=" + updateTime + ", remark="
				+ remark + ", status=" + status + ", upset=" + upset + "]";
	}
    
    
}