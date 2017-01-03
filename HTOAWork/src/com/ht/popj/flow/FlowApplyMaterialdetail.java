package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;

public class FlowApplyMaterialdetail {
    private Integer id;

    private Integer materialid;

    private Integer flowmodelid;

    private String empid;
    
    private String empname;
    
    private Material material;
    
    private FlowApplyMaterial flowApplyMaterial;

    private Emp emp;
    
    private FlowsModel flowmodel;
    
    private String remark;
    
    private Integer status;

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
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

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public FlowApplyMaterial getFlowApplyMaterial() {
		return flowApplyMaterial;
	}

	public void setFlowApplyMaterial(FlowApplyMaterial flowApplyMaterial) {
		this.flowApplyMaterial = flowApplyMaterial;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public FlowsModel getFlowmodel() {
		return flowmodel;
	}

	public void setFlowmodel(FlowsModel flowmodel) {
		this.flowmodel = flowmodel;
	}

    
}