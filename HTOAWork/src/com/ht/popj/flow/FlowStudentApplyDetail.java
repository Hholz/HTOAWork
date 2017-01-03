package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;

public class FlowStudentApplyDetail {
    private Integer id;

    private Integer studapplyid;

    private Integer flowmodelid;

    private String empid;

    private Integer status;

    private String remark;
    
    private Emp emp;
    
    private FlowsModel flowModel;
    
    private FlowStudentApply studentApply;

    public FlowStudentApply getStudentApply() {
		return studentApply;
	}

	public void setStudentApply(FlowStudentApply studentApply) {
		this.studentApply = studentApply;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public FlowsModel getFlowModel() {
		return flowModel;
	}

	public void setFlowModel(FlowsModel flowModel) {
		this.flowModel = flowModel;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudapplyid() {
        return studapplyid;
    }

    public void setStudapplyid(Integer studapplyid) {
        this.studapplyid = studapplyid;
    }

    public Integer getFlowmodelid() {
        return flowmodelid;
    }

    public void setFlowmodelid(Integer flowmodelid) {
        this.flowmodelid = flowmodelid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
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