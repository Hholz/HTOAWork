package com.ht.popj.dailyWork;

import com.ht.popj.student.StudentHourse;

public class PatrolHouse {
    private Integer id;

    private String houseid;

    private Integer mustarrive;

    private Integer truearrive;

    private String clean;

    private String patrolteach;

    private String patroltime;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private Emp emp;
    
    private StudentHourse house;

    public StudentHourse getHouse() {
		return house;
	}

	public void setHouse(StudentHourse house) {
		this.house = house;
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

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public Integer getMustarrive() {
        return mustarrive;
    }

    public void setMustarrive(Integer mustarrive) {
        this.mustarrive = mustarrive;
    }

    public Integer getTruearrive() {
        return truearrive;
    }

    public void setTruearrive(Integer truearrive) {
        this.truearrive = truearrive;
    }

    public String getClean() {
        return clean;
    }

    public void setClean(String clean) {
        this.clean = clean == null ? null : clean.trim();
    }

    public String getPatrolteach() {
        return patrolteach;
    }

    public void setPatrolteach(String patrolteach) {
        this.patrolteach = patrolteach == null ? null : patrolteach.trim();
    }

    public String getPatroltime() {
        return patroltime;
    }

    public void setPatroltime(String patroltime) {
        this.patroltime = patroltime;
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
        this.remark = remark;
    }
}