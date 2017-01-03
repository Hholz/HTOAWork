package com.ht.popj.student;

import java.util.Date;

public class StuRepScore {
    private Integer id;

    private Integer stuId;

    private String teacName;

    private Integer srmId;

    private Integer srsScore;

    private String srsDate;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private Student student;
    //<!-- 模板信息 -->
    private StuReplyModel stuReplyModel;
    //<!-- 模板总分 -->
    private Integer allScore;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getTeacName() {
        return teacName;
    }

    public void setTeacName(String teacName) {
        this.teacName = teacName == null ? null : teacName.trim();
    }

    public Integer getSrmId() {
        return srmId;
    }

    public void setSrmId(Integer srmId) {
        this.srmId = srmId;
    }

    public Integer getSrsScore() {
        return srsScore;
    }

    public void setSrsScore(Integer srsScore) {
        this.srsScore = srsScore;
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public StuReplyModel getStuReplyModel() {
		return stuReplyModel;
	}

	public void setStuReplyModel(StuReplyModel stuReplyModel) {
		this.stuReplyModel = stuReplyModel;
	}

	public Integer getAllScore() {
		return allScore;
	}

	public void setAllScore(Integer allScore) {
		this.allScore = allScore;
	}

	public String getSrsDate() {
		if(null!=srsDate&&!srsDate.isEmpty()&&srsDate.length()>=10){
			return srsDate.substring(0, 10);
		}else{
			return srsDate;
		}
	}

	public void setSrsDate(String srsDate) {
		this.srsDate = srsDate;
	}
}