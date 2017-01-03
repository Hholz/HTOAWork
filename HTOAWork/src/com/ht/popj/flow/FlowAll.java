package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.Student;

public class FlowAll {
    private Integer id;

    private Integer flowmodeltypeid;

    private String createTime;

    private String empid;

    private Integer studid;

    private Integer status;

    private String remark;
    
    private Integer applyid;
    
    private Integer depid;
    
    private String startTime;
    
    private String endTime;
    
    private Integer classid;
    
    private Integer fallid;
    
    private Emp emp;
    
    private Student student;
    
    private FlowsModeltype modelType;

	public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	public Integer getFallid() {
		return fallid;
	}

	public void setFallid(Integer fallid) {
		this.fallid = fallid;
	}

	public Integer getDepid() {
		return depid;
	}

	public void setDepid(Integer depid) {
		this.depid = depid;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

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

	public FlowsModeltype getModelType() {
		return modelType;
	}

	public void setModelType(FlowsModeltype modelType) {
		this.modelType = modelType;
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

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public Integer getApplyid() {
		return applyid;
	}

	public void setApplyid(Integer applyid) {
		this.applyid = applyid;
	}
    
}