package com.ht.popj.dailyWork;

import java.util.Date;

public class Works {
    private Integer id;

    private String empid;

    private String companyname;

    private String degree;

    private String startdate;

    private String enddate;

    private String reason;

    private String worksRemark;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String remark;
    
    private Emp emp;

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

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname == null ? null : companyname.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getWorksRemark() {
        return worksRemark;
    }

    public void setWorksRemark(String worksRemark) {
        this.worksRemark = worksRemark == null ? null : worksRemark.trim();
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

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	@Override
	public String toString() {
		return "Works [id=" + id + ", empid=" + empid + ", companyname=" + companyname + ", degree=" + degree
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", reason=" + reason + ", worksRemark="
				+ worksRemark + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status
				+ ", remark=" + remark + "]";
	}
    
}