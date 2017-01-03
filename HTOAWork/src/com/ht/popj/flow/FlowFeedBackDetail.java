package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;

public class FlowFeedBackDetail {
    private Integer id;

    private Integer feedbackid;

    private Integer flowmodelid;

    private String empid;

    private Integer status;

    private String remark;
    
    private FlowsModel flowModel;
    
    private Emp emp;
    
    private FlowFeedBack feedBack;

	public FlowFeedBack getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(FlowFeedBack feedBack) {
		this.feedBack = feedBack;
	}

	public FlowsModel getFlowModel() {
		return flowModel;
	}

	public void setFlowModel(FlowsModel flowModel) {
		this.flowModel = flowModel;
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

    public Integer getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(Integer feedbackid) {
        this.feedbackid = feedbackid;
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

	@Override
	public String toString() {
		return "FlowFeedBackDetail [id=" + id + ", feedbackid=" + feedbackid + ", flowmodelid=" + flowmodelid
				+ ", empid=" + empid + ", status=" + status + ", remark=" + remark + "]";
	}
    
    
}