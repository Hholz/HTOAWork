package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;

public class FlowSupplementDetail {
    private Integer id;

    private Integer sumentid;

    private Integer flowmodelid;

    private String empid;
    
    private FlowSupplement flowSupplement;
    
    private Emp emp;
    
    private FlowsModel flowModel;

    private String remark;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSumentid() {
        return sumentid;
    }

    public void setSumentid(Integer sumentid) {
        this.sumentid = sumentid;
    }

    public FlowsModel getFlowModel() {
		return flowModel;
	}

	public void setFlowModel(FlowsModel flowModel) {
		this.flowModel = flowModel;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public FlowSupplement getFlowSupplement() {
		return flowSupplement;
	}

	public void setFlowSupplement(FlowSupplement flowSupplement) {
		this.flowSupplement = flowSupplement;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}
    
    
}