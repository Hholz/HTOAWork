package com.ht.popj.student;

import java.util.Date;

import com.ht.popj.dailyWork.Emp;

public class StuReplyModel {
    private Integer id;

    private String srmName;

    private String srmDescr;

    private Integer clsId;

    private String teacId;

    private String repTime;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private Emp teac;
    private StudentClass cls;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrmName() {
        return srmName;
    }

    public void setSrmName(String srmName) {
        this.srmName = srmName == null ? null : srmName.trim();
    }

    public String getSrmDescr() {
        return srmDescr;
    }

    public void setSrmDescr(String srmDescr) {
        this.srmDescr = srmDescr == null ? null : srmDescr.trim();
    }

    public Integer getClsId() {
        return clsId;
    }

    public void setClsId(Integer clsId) {
        this.clsId = clsId;
    }

    public String getTeacId() {
        return teacId;
    }

    public void setTeacId(String teacId) {
        this.teacId = teacId == null ? null : teacId.trim();
    }

    public String getRepTime() {
    	if(null!=repTime&&!repTime.isEmpty()){
			return repTime.substring(0, 10);
		}else{
			return repTime;
		}
    }

    public void setRepTime(String repTime) {
        this.repTime = repTime;
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

	public StudentClass getCls() {
		return cls;
	}

	public void setCls(StudentClass cls) {
		this.cls = cls;
	}

	public Emp getTeac() {
		return teac;
	}

	public void setTeac(Emp teac) {
		this.teac = teac;
	}
}