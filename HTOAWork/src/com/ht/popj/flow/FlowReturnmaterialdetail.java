package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;

public class FlowReturnmaterialdetail {
    private Integer id;

    private Integer returnmaterid;

    private Integer flowmodelid;
    
    private FlowReturnmaterial flowreturnmaterial;
    
    private FlowsModel flowmodel;
    
    private Emp emp;
    
    private Material material;

    private String empid;

    private String remark;
    
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReturnmaterid() {
        return returnmaterid;
    }

    public void setReturnmaterid(Integer returnmaterid) {
        this.returnmaterid = returnmaterid;
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

	public FlowReturnmaterial getFlowreturnmaterial() {
		return flowreturnmaterial;
	}

	public void setFlowreturnmaterial(FlowReturnmaterial flowreturnmaterial) {
		this.flowreturnmaterial = flowreturnmaterial;
	}

	public FlowsModel getFlowmodel() {
		return flowmodel;
	}

	public void setFlowmodel(FlowsModel flowmodel) {
		this.flowmodel = flowmodel;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}