package com.ht.popj.student;

import java.util.Date;

public class StuRepScoreD {
    private Integer id;

    private Integer srsId;

    private Integer srmId;

    private Integer srmdId;

    private Integer srsdScore;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private StuReplyModelD stuReplyModelD;
    
    private StuRepScore srs;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSrsId() {
        return srsId;
    }

    public void setSrsId(Integer srsId) {
        this.srsId = srsId;
    }

    public Integer getSrmId() {
        return srmId;
    }

    public void setSrmId(Integer srmId) {
        this.srmId = srmId;
    }

    public Integer getSrmdId() {
        return srmdId;
    }

    public void setSrmdId(Integer srmdId) {
        this.srmdId = srmdId;
    }

    public Integer getSrsdScore() {
        return srsdScore;
    }

    public void setSrsdScore(Integer srsdScore) {
        this.srsdScore = srsdScore;
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

	public StuReplyModelD getStuReplyModelD() {
		return stuReplyModelD;
	}

	public void setStuReplyModelD(StuReplyModelD stuReplyModelD) {
		this.stuReplyModelD = stuReplyModelD;
	}

	public StuRepScore getSrs() {
		return srs;
	}

	public void setSrs(StuRepScore srs) {
		this.srs = srs;
	}
}