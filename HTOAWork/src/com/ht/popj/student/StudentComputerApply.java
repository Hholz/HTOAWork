package com.ht.popj.student;

import java.util.Date;

import com.ht.popj.sysSet.FlowModelType;


public class StudentComputerApply {
    private Integer id;

    private Integer studentid;

    private String applyno;
    
    private String createman;

    private String applytime;

    private Integer flowid;

    private Integer stat;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;

    private Integer managetype;
    
    private ComputerManage computerManage;
    
    private FlowModelType flowmodeltype;
    
    private Student student;
    
    public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}


	public FlowModelType getFlowmodeltype() {
		return flowmodeltype;
	}

	public void setFlowmodeltype(FlowModelType flowmodeltype) {
		this.flowmodeltype = flowmodeltype;
	}

	public ComputerManage getComputerManage() {
		return computerManage;
	}

	public void setComputerManage(ComputerManage computerManage) {
		this.computerManage = computerManage;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateman() {
		return createman;
	}

	public void setCreateman(String createman) {
		this.createman = createman;
	}

	public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno == null ? null : applyno.trim();
    }

    public String getApplytime() {
		return applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public Integer getFlowid() {
        return flowid;
    }

    public void setFlowid(Integer flowid) {
        this.flowid = flowid;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
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

	public Integer getManagetype() {
		return managetype;
	}

	public void setManagetype(Integer managetype) {
		this.managetype = managetype;
	}

}