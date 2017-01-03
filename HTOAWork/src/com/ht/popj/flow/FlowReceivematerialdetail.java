package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;

public class FlowReceivematerialdetail {
    private Integer id;

    private Integer receivematerid;

    private Integer flowmodelid;

    private String empid;
    
    private FlowReceivematerial flowReceivematerial;
    
    private FlowsModel flowmodel;
    
    private Emp emp;
    
    private Material material;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceivematerid() {
        return receivematerid;
    }

    public void setReceivematerid(Integer receivematerid) {
        this.receivematerid = receivematerid;
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

	public FlowReceivematerial getFlowReceivematerial() {
		return flowReceivematerial;
	}

	public void setFlowReceivematerial(FlowReceivematerial flowReceivematerial) {
		this.flowReceivematerial = flowReceivematerial;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public FlowsModel getFlowmodel() {
		return flowmodel;
	}

	public void setFlowmodel(FlowsModel flowmodel) {
		this.flowmodel = flowmodel;
	}

    
}