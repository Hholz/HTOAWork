package com.ht.popj.dailyWork;

import java.util.Date;

public class Worklog {
    private Integer id;

    private String empid;

    private String workday;

    private Integer courseid;
    
    private Integer classid;

    private Integer process;

    private Integer hour;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String remark;

    private String contents;
    
    private String empname;
    
    private String educoursename;
    
    private String eduoutlinename;
    
    private String classname;

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

    public String getWorkday() {
        return workday;
    }

    public void setWorkday(String workday) {
        this.workday = workday;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getEducoursename() {
		return educoursename;
	}

	public void setEducoursename(String educoursename) {
		this.educoursename = educoursename;
	}

	public String getEduoutlinename() {
		return eduoutlinename;
	}

	public void setEduoutlinename(String eduoutlinename) {
		this.eduoutlinename = eduoutlinename;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Override
	public String toString() {
		return "Worklog [id=" + id + ", empid=" + empid + ", workday=" + workday + ", courseid=" + courseid
				+ ", classid=" + classid + ", process=" + process + ", hour=" + hour + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + ", remark=" + remark + ", contents=" + contents
				+ ", empname=" + empname + ", educoursename=" + educoursename + ", eduoutlinename=" + eduoutlinename
				+ ", classname=" + classname + "]";
	}

}