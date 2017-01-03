package com.ht.popj.dailyWork;

import java.util.Date;

public class Attenstatis {
    private Integer id;

    private Integer finger;

    private String empname;

    private Integer depid;

    private String workday;

    private Integer attenstatus;

    private Integer late;

    private Float absent;

    private Float leave;

    private Date updatetime;

    private String remark;

    private Integer status;
    
    private String endday;
    
    private Dep dep ;
    
    private int abshalf;
    
    private int abs_all;
    
    private int leahalf;
    
    private int leave_all;
    
    private int late_all;
    
    private int abnormal;//Òì³£
    
    private int normal;//Õý³£
    
    private int fclockin;
    
    

    public int getFclockin() {
		return fclockin;
	}

	public void setFclockin(int fclockin) {
		this.fclockin = fclockin;
	}

	public int getAbshalf() {
		return abshalf;
	}

	public void setAbshalf(int abshalf) {
		this.abshalf = abshalf;
	}

	public int getAbs_all() {
		return abs_all;
	}

	public void setAbs_all(int abs_all) {
		this.abs_all = abs_all;
	}

	public int getLeahalf() {
		return leahalf;
	}

	public void setLeahalf(int leahalf) {
		this.leahalf = leahalf;
	}

	public int getLeave_all() {
		return leave_all;
	}

	public void setLeave_all(int leave_all) {
		this.leave_all = leave_all;
	}

	public int getLate_all() {
		return late_all;
	}

	public void setLate_all(int late_all) {
		this.late_all = late_all;
	}

	public int getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(int abnormal) {
		this.abnormal = abnormal;
	}

	public int getNormal() {
		return normal;
	}

	public void setNormal(int normal) {
		this.normal = normal;
	}

	public Dep getDep() {
		return dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

	public String getEndday() {
		return endday;
	}

	public void setEndday(String endday) {
		this.endday = endday;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFinger() {
        return finger;
    }

    public void setFinger(Integer finger) {
        this.finger = finger;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public String getWorkday() {
		return workday;
	}
    
    public void setWorkday(String workday) {
        this.workday = workday;
    }

	public Integer getAttenstatus() {
        return attenstatus;
    }

    public void setAttenstatus(Integer attenstatus) {
        this.attenstatus = attenstatus;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public Float getAbsent() {
        return absent;
    }

    public void setAbsent(Float absent) {
        this.absent = absent;
    }

    public Float getLeave() {
        return leave;
    }

    public void setLeave(Float leave) {
        this.leave = leave;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}