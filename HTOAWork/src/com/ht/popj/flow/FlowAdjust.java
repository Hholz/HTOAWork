package com.ht.popj.flow;

import java.util.Date;

public class FlowAdjust {
    private Integer id;

    private Integer flowmodeltypeid;

    private Integer empid;

    private Integer daycounts;

    private Integer upset;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

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

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public Integer getDaycounts() {
        return daycounts;
    }

    public void setDaycounts(Integer daycounts) {
        this.daycounts = daycounts;
    }

    public Integer getUpset() {
        return upset;
    }

    public void setUpset(Integer upset) {
        this.upset = upset;
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

	@Override
	public String toString() {
		return "FlowAdjust [id=" + id + ", flowmodeltypeid=" + flowmodeltypeid + ", empid=" + empid + ", daycounts="
				+ daycounts + ", upset=" + upset + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", status=" + status + ", remark=" + remark + "]";
	}
    
}