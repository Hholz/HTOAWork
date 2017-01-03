package com.ht.popj.flow;

import java.util.Date;

public class FlowsModeltype {
    private Integer id;

    private String flowmodeltype;

    private Integer invalid;
    
    private Integer flowType;

    private Integer modelselid;
    
    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private FlowModelsel flowModelsel;

	public Integer getFlowType() {
		return flowType;
	}

	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlowmodeltype() {
        return flowmodeltype;
    }

    public void setFlowmodeltype(String flowmodeltype) {
        this.flowmodeltype = flowmodeltype == null ? null : flowmodeltype.trim();
    }

    public Integer getInvalid() {
        return invalid;
    }

    public FlowModelsel getFlowModelsel() {
		return flowModelsel;
	}

	public void setFlowModelsel(FlowModelsel flowModelsel) {
		this.flowModelsel = flowModelsel;
	}

	public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getModelselid() {
        return modelselid;
    }

    public void setModelselid(Integer modelselid) {
        this.modelselid = modelselid;
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
		return "FlowModeltype [id=" + id + ", flowmodeltype=" + flowmodeltype + ", invalid=" + invalid + ", modelselid="
				+ modelselid + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status
				+ ", remark=" + remark + "]";
	}
    
}