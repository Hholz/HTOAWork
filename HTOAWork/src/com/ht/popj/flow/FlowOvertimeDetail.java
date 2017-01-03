package com.ht.popj.flow;

public class FlowOvertimeDetail {
    private Integer id;

    private Integer overtimeid;

    private Integer flowmodelid;

    private String empid;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOvertimeid() {
        return overtimeid;
    }

    public void setOvertimeid(Integer overtimeid) {
        this.overtimeid = overtimeid;
    }

    public Integer getFlowmodelid() {
        return flowmodelid;
    }

    public void setFlowmodelid(Integer flowmodelid) {
        this.flowmodelid = flowmodelid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
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
		return "FlowOvertimeDetail [id=" + id + ", overtimeid=" + overtimeid + ", flowmodelid=" + flowmodelid
				+ ", empid=" + empid + ", status=" + status + ", remark=" + remark + "]";
	}
    
    
}