package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.Student;

public class FlowHoliday {
    private Integer id;

    private Integer flowmodeltypeid;

    private String empid;

    private Integer studid;

    private String strattime;

    private String endtime;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;

    private Integer upset;
    
    private Student student;
    
    private Emp emp;
    
    private String applyman;

    public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

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

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public Integer getStudid() {
        return studid;
    }

    public void setStudid(Integer studid) {
        this.studid = studid;
    }

    public String getStrattime() {
		return strattime;
	}

	public void setStrattime(String strattime) {
		this.strattime = strattime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
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

    public Integer getUpset() {
        return upset;
    }

    public void setUpset(Integer upset) {
        this.upset = upset;
    }

	public String getApplyman() {
		return applyman;
	}

	public void setApplyman(String applyman) {
		this.applyman = applyman;
	}
    
    
}