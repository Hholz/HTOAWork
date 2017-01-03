package com.ht.popj.flow;

import java.util.Date;

public class Waitformaterial {
    private Integer id;

    private Integer materialid;
    
    private String materialname;

    private Integer count;

    private Integer applystatus;
    
    private String applyman;
    
    private String empname;

    private Integer applymaterialid;

    private String createtime;

    private String updatetime;

    private Integer status;

    private String remark;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getApplystatus() {
        return applystatus;
    }

    public void setApplystatus(Integer applystatus) {
        this.applystatus = applystatus;
    }

    public String getApplyman() {
        return applyman;
    }

    public void setApplyman(String applyman) {
        this.applyman = applyman == null ? null : applyman.trim();
    }

    public Integer getApplymaterialid() {
        return applymaterialid;
    }

    public void setApplymaterialid(Integer applymaterialid) {
        this.applymaterialid = applymaterialid;
    }

    public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
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

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	@Override
	public String toString() {
		return "Waitformaterial [id=" + id + ", materialid=" + materialid + ", materialname=" + materialname
				+ ", count=" + count + ", applystatus=" + applystatus + ", applyman=" + applyman + ", empname="
				+ empname + ", applymaterialid=" + applymaterialid + ", createtime=" + createtime + ", updatetime="
				+ updatetime + ", status=" + status + ", remark=" + remark + "]";
	}
    
    
}