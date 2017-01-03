package com.ht.popj.student;

import java.util.Date;

public class StuReplyModelD {
    private Integer id;

    private Integer srmId;

    private String srmdName;

    private Integer srmdScore;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private StuReplyModel srm;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSrmId() {
        return srmId;
    }

    public void setSrmId(Integer srmId) {
        this.srmId = srmId;
    }

    public String getSrmdName() {
        return srmdName;
    }

    public void setSrmdName(String srmdName) {
        this.srmdName = srmdName == null ? null : srmdName.trim();
    }

    public Integer getSrmdScore() {
        return srmdScore;
    }

    public void setSrmdScore(Integer srmdScore) {
        this.srmdScore = srmdScore;
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

    public Integer getstatus() {
        return status;
    }

    public void setstatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public StuReplyModel getSrm() {
		return srm;
	}

	public void setSrm(StuReplyModel srm) {
		this.srm = srm;
	}
}