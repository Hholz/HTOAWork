package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;

public class FlowReceivematerial {
    private Integer id;

    private String empid;
    
    private String empname;
    
    private Emp emp;
    
    private Material material;
    
    private Integer counts;

    private Integer materialid;

    private Integer flowmodeltypeid;

    private Integer upset;

    private String createTime;

    private String updateTime;
    
    private String receiveremark;
    
    private Integer approvestatus;

    private Integer status;

    private String remark;
    
    private String applyman;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public Integer getFlowmodeltypeid() {
        return flowmodeltypeid;
    }

    public void setFlowmodeltypeid(Integer flowmodeltypeid) {
        this.flowmodeltypeid = flowmodeltypeid;
    }

    public Integer getUpset() {
        return upset;
    }

    public void setUpset(Integer upset) {
        this.upset = upset;
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

	public String getReceiveremark() {
		return receiveremark;
	}

	public void setReceiveremark(String receiveremark) {
		this.receiveremark = receiveremark;
	}

	public Integer getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getApplyman() {
		return applyman;
	}

	public void setApplyman(String applyman) {
		this.applyman = applyman;
	}

    
}