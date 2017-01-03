package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;

public class FlowSwaparrangeDetail {
    private Integer id;

    private Integer swaparrangeid;

    private Integer flowmodelid;

    private String empid;

    private Integer status;

    private String remark;
    
    private Emp emp;
    
    private FlowsModel flowModel;
    
    private FlowSwaparrange flowSwaparrange;
    
    

    public FlowSwaparrange getFlowSwaparrange() {
		return flowSwaparrange;
	}

	public void setFlowSwaparrange(FlowSwaparrange flowSwaparrange) {
		this.flowSwaparrange = flowSwaparrange;
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

    public Integer getSwaparrangeid() {
        return swaparrangeid;
    }

    public void setSwaparrangeid(Integer swaparrangeid) {
        this.swaparrangeid = swaparrangeid;
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
		return "FlowSwaparrangeDetail [id=" + id + ", swaparrangeid=" + swaparrangeid + ", flowmodelid=" + flowmodelid
				+ ", empid=" + empid + ", status=" + status + ", remark=" + remark + "]";
	}
    
    
}