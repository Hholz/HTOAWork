package com.ht.popj.dailyWork;

import java.util.Date;

public class Dutymodeldetail {
    private Integer id;

    private Integer modelid;

    private String weekends;

    private Integer empType;

    private String empName;
    
    private String empScope;
    
    private String empPhone;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private Dutymodel dutymodel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelid() {
        return modelid;
    }

    public void setModelid(Integer modelid) {
        this.modelid = modelid;
    }

    public String getWeekends() {
        return weekends;
    }

    public void setWeekends(String weekends) {
        this.weekends = weekends == null ? null : weekends.trim();
    }

    public Integer getEmpType() {
        return empType;
    }

    public void setEmpType(Integer empType) {
        this.empType = empType;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

	public Dutymodel getDutymodel() {
		return dutymodel;
	}

	public void setDutymodel(Dutymodel dutymodel) {
		this.dutymodel = dutymodel;
	}

	public String getEmpScope() {
		return empScope;
	}

	public void setEmpScope(String empScope) {
		this.empScope = empScope;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

}