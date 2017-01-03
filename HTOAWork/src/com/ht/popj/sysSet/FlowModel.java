package com.ht.popj.sysSet;

import java.util.Date;
import java.util.List;

public class FlowModel {
    private Integer id;

    private Integer flowmodeltypeid;
    
    private String flowmodeltype;
    
    private String flowmodelname;
    
    private String primarystep;
    
    private FlowModelType type;

    private Integer planday;
    
    private String typename;

    private Integer invalid;

    private Integer seq;

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

    public String getFlowmodeltype() {
		return flowmodeltype;
	}

	public FlowModelType getType() {
		return type;
	}

	public void setType(FlowModelType type) {
		this.type = type;
	}

	public void setFlowmodeltype(String flowmodeltype) {
		this.flowmodeltype = flowmodeltype;
	}

	public String getFlowmodelname() {
        return flowmodelname;
    }

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public void setFlowmodelname(String flowmodelname) {
        this.flowmodelname = flowmodelname == null ? null : flowmodelname.trim();
    }

    public String getPrimarystep() {
        return primarystep;
    }

	public void setPrimarystep(String primarystep) {
        this.primarystep = primarystep == null ? null : primarystep.trim();
    }

    public Integer getPlanday() {
        return planday;
    }

    public void setPlanday(Integer planday) {
        this.planday = planday;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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