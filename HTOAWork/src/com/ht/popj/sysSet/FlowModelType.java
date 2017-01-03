package com.ht.popj.sysSet;

import java.util.Date;
import java.util.List;

public class FlowModelType {
    private Integer id;

    private String flowmodeltype;

    private Integer invalid;
    
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

    public String getFlowmodeltype() {
        return flowmodeltype;
    }

    public void setFlowmodeltype(String flowmodeltype) {
        this.flowmodeltype = flowmodeltype == null ? null : flowmodeltype.trim();
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

}