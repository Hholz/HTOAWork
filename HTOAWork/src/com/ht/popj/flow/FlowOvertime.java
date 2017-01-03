package com.ht.popj.flow;

import java.math.BigDecimal;
import java.util.Date;

public class FlowOvertime {
    private Integer id;

    private Integer flowmodeltypeid;

    private Integer empid;

    private BigDecimal holidayday;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private Integer status;

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

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public BigDecimal getHolidayday() {
        return holidayday;
    }

    public void setHolidayday(BigDecimal holidayday) {
        this.holidayday = holidayday;
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
		return "FlowOvertime [id=" + id + ", flowmodeltypeid=" + flowmodeltypeid + ", empid=" + empid + ", holidayday="
				+ holidayday + ", createTime=" + createTime + ", updateTime=" + updateTime + ", remark=" + remark
				+ ", status=" + status + ", upset=" + upset + "]";
	}
    
}