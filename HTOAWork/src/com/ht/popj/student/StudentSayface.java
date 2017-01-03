package com.ht.popj.student;

import java.util.Date;

import com.ht.popj.dailyWork.Emp;

public class StudentSayface {
    private Integer id;

    private Integer sayfaceid;

    private String teacherid;

    private String sayscon;

    private String sayproblem;

    private String saytime;

    private String sayback;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private Emp emp;
    
    private Student student;
    
    

    public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSayfaceid() {
        return sayfaceid;
    }

    public void setSayfaceid(Integer sayfaceid) {
        this.sayfaceid = sayfaceid;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid == null ? null : teacherid.trim();
    }

    public String getSayscon() {
        return sayscon;
    }

    public void setSayscon(String sayscon) {
        this.sayscon = sayscon == null ? null : sayscon.trim();
    }

    public String getSayproblem() {
        return sayproblem;
    }

    public void setSayproblem(String sayproblem) {
        this.sayproblem = sayproblem == null ? null : sayproblem.trim();
    }


    public String getSaytime() {
		return saytime;
	}

	public void setSaytime(String saytime) {
		this.saytime = saytime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getSayback() {
        return sayback;
    }

    public void setSayback(String sayback) {
        this.sayback = sayback == null ? null : sayback.trim();
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