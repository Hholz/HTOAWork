package com.ht.popj.student;

import java.util.Date;

public class AdjustClass {
    private Integer id;

    private Integer stuId;

    private Integer clsid;

    private Integer toclsid;

    private String reason;

    private Integer acStatus;

    private String createTime;

    private String updateTime;

    private Integer stuatus;

    private String remark;

    private StudentClass cls;
    
    private StudentClass tocls;
    
    private Student student;
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

    public Integer getClsid() {
        return clsid;
    }

    public void setClsid(Integer clsid) {
        this.clsid = clsid;
    }

    public Integer getToclsid() {
        return toclsid;
    }

    public void setToclsid(Integer toclsid) {
        this.toclsid = toclsid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(Integer acStatus) {
        this.acStatus = acStatus;
    }
    
   

	public String getCreateTime() {
		if(null!=createTime&&!createTime.isEmpty()){
			return createTime.substring(0, 10);
		}else{
			return createTime;
		}
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		if(null!=updateTime&&!updateTime.isEmpty()){
			return updateTime.substring(0, 10);
		}else{
			return updateTime;
		}
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

    public Integer getStuatus() {
        return stuatus;
    }

    public void setStuatus(Integer stuatus) {
        this.stuatus = stuatus;
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

	public StudentClass getTocls() {
		return tocls;
	}

	public void setTocls(StudentClass tocls) {
		this.tocls = tocls;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}