package com.ht.popj.flow;

import java.util.Date;

public class FlowModelsel {
    private Integer id;

    private String modelname;

    private String modelselname;
    
    private Integer flowType;

    private Integer invalid;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

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

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname == null ? null : modelname.trim();
    }

    public String getModelselname() {
        return modelselname;
    }

    public void setModelselname(String modelselname) {
        this.modelselname = modelselname == null ? null : modelselname.trim();
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
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
		return "FlowModelsel [id=" + id + ", modelname=" + modelname + ", modelselname=" + modelselname + ", invalid="
				+ invalid + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status
				+ ", remark=" + remark + "]";
	}
    
}