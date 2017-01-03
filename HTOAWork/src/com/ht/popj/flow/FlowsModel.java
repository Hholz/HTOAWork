package com.ht.popj.flow;

import java.util.Date;

import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.sysSet.FlowModelType;

public class FlowsModel {
    private Integer id;

    private Integer flowmodeltypeid;

    private String flowmodelname;

    private Integer planday;
    
    private String empid;
    
    private String roleid;

    private Integer invalid;

    private Integer seq;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private Integer depid;
    
    private FlowModelType modelType;
    
    private Emp emp;
    
    private Dep dep;
    
    private FlowRole flowRole;

	public Integer getDepid() {
		return depid;
	}

	public void setDepid(Integer depid) {
		this.depid = depid;
	}

	public Dep getDep() {
		return dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

	public FlowRole getFlowRole() {
		return flowRole;
	}

	public void setFlowRole(FlowRole flowRole) {
		this.flowRole = flowRole;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
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

    public Integer getFlowmodeltypeid() {
        return flowmodeltypeid;
    }

    public void setFlowmodeltypeid(Integer flowmodeltypeid) {
        this.flowmodeltypeid = flowmodeltypeid;
    }

    public String getFlowmodelname() {
        return flowmodelname;
    }

    public void setFlowmodelname(String flowmodelname) {
        this.flowmodelname = flowmodelname == null ? null : flowmodelname.trim();
    }

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public Integer getPlanday() {
        return planday;
    }

    public void setPlanday(Integer planday) {
        this.planday = planday;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

	public FlowModelType getModelType() {
		return modelType;
	}

	public void setModelType(FlowModelType modelType) {
		this.modelType = modelType;
	}
}