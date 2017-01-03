package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;

public class FlowBaoxiaodetail {
	private Integer id;

    private Integer baoxiaoid;

    private Integer flowmodelid;

    private String empid;

    private Integer status;

    private String remark;
    
    private FlowBaoxiao flowBaoxiao;
    
    private Emp emp;
    
    private FlowsModel flowModel;
    
    

    public FlowsModel getFlowModel() {
		return flowModel;
	}

	public void setFlowModel(FlowsModel flowModel) {
		this.flowModel = flowModel;
	}

	public FlowBaoxiao getFlowBaoxiao() {
		return flowBaoxiao;
	}

	public void setFlowBaoxiao(FlowBaoxiao flowBaoxiao) {
		this.flowBaoxiao = flowBaoxiao;
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

    public Integer getBaoxiaoid() {
        return baoxiaoid;
    }

    public void setBaoxiaoid(Integer baoxiaoid) {
        this.baoxiaoid = baoxiaoid;
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