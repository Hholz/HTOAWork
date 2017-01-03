package com.ht.popj.dailyWork;

import com.ht.popj.student.StudentClass;

public class PatrolClass {
    private Integer id;

    private Integer classid;

    private Integer mustarrive;

    private Integer truearrive;

    private String empteachid;

    private String inthework;

    private String stubrerule;

    private String patrolteach;

    private String patroltime;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private Emp emp;
    private Emp emp1;
    
    public Emp getEmp1() {
		return emp1;
	}

	public void setEmp1(Emp emp1) {
		this.emp1 = emp1;
	}

	private StudentClass stuclass;

	public StudentClass getStuclass() {
		return stuclass;
	}

	public void setStuclass(StudentClass stuclass) {
		this.stuclass = stuclass;
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

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
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

    public String getEmpteachid() {
        return empteachid;
    }

    public void setEmpteachid(String empteachid) {
        this.empteachid = empteachid;
    }

    public String getInthework() {
        return inthework;
    }

    public void setInthework(String inthework) {
        this.inthework = inthework == null ? null : inthework.trim();
    }

    public String getStubrerule() {
        return stubrerule;
    }

    public void setStubrerule(String stubrerule) {
        this.stubrerule = stubrerule == null ? null : stubrerule.trim();
    }

    public String getPatrolteach() {
        return patrolteach;
    }

    public void setPatrolteach(String patrolteach) {
        this.patrolteach = patrolteach;
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
        this.remark = remark == null ? null : remark.trim();
    }
}