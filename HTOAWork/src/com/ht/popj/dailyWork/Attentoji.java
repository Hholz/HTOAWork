package com.ht.popj.dailyWork;

import java.util.Date;

public class Attentoji {
    private Integer id;

    private Integer finger;

    private String empname;

    private Integer depid;

    private String worktime;

    private Integer lates;

    private Integer absents;

    private Integer leavecs;

    private Integer status;
    
    private Dep dep;
    
    

    public Dep getDep() {
		return dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFinger() {
        return finger;
    }

    public void setFinger(Integer finger) {
        this.finger = finger;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	public Integer getLates() {
        return lates;
    }

    public void setLates(Integer lates) {
        this.lates = lates;
    }

    public Integer getAbsents() {
        return absents;
    }

    public void setAbsents(Integer absents) {
        this.absents = absents;
    }

    public Integer getLeavecs() {
        return leavecs;
    }

    public void setLeavecs(Integer leavecs) {
        this.leavecs = leavecs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}