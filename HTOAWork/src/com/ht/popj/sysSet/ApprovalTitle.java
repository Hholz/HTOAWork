package com.ht.popj.sysSet;

import java.util.Date;

public class ApprovalTitle {
    private Integer id;

    private String approvalnum;

    private String createman;
    
    private String creman;

    private String approvalman;
    
    private String appman;
    
    private String semp;
    
    private String empid;

    private Integer depid;
    
    private String depname;

    private String titlestutas;

    private Integer flowid;

    private Date createtime;

    private Date updatetime;

    private Integer stutas;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApprovalnum() {
        return approvalnum;
    }

    public void setApprovalnum(String approvalnum) {
        this.approvalnum = approvalnum == null ? null : approvalnum.trim();
    }


    public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getSemp() {
		return semp;
	}

	public void setSemp(String semp) {
		this.semp = semp;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getCreateman() {
		return createman;
	}

	public void setCreateman(String createman) {
		this.createman = createman;
	}

	public String getApprovalman() {
		return approvalman;
	}

	public void setApprovalman(String approvalman) {
		this.approvalman = approvalman;
	}

	public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public String getCreman() {
		return creman;
	}

	public void setCreman(String creman) {
		this.creman = creman;
	}

	public String getAppman() {
		return appman;
	}

	public void setAppman(String appman) {
		this.appman = appman;
	}

	public String getTitlestutas() {
        return titlestutas;
    }

    public void setTitlestutas(String titlestutas) {
        this.titlestutas = titlestutas == null ? null : titlestutas.trim();
    }

    public Integer getFlowid() {
        return flowid;
    }

    public void setFlowid(Integer flowid) {
        this.flowid = flowid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getStutas() {
        return stutas;
    }

    public void setStutas(Integer stutas) {
        this.stutas = stutas;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}