package com.ht.popj.flow;

import java.util.Date;

public class FlowComputerRepair {
    private Integer id;

    private Integer flowmodeltypeid;

    private Integer studid;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private Integer upset;

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

    public Integer getStudid() {
        return studid;
    }

    public void setStudid(Integer studid) {
        this.studid = studid;
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

    public Integer getUpset() {
        return upset;
    }

    public void setUpset(Integer upset) {
        this.upset = upset;
    }

	@Override
	public String toString() {
		return "FlowComputerRepair [id=" + id + ", flowmodeltypeid=" + flowmodeltypeid + ", studid=" + studid
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + ", remark="
				+ remark + ", upset=" + upset + "]";
	}
    
}