package com.ht.popj.dailyWork;

import java.util.Date;

public class ReturnMaterial {
	private Integer id;

	private Integer materialid;

	private Integer materialtypeid;

	private String materialname;

	private String unit;

	private String materialtypename;

	private String empname;

	private Integer counts;

	private String empid;

	private String createtime;

	private String updatetime;

	private Integer status;

	private String remark;

	private Integer upset;

	private Integer flowid;

	private String appnum;

	private Integer approvalstatus;

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

	public Integer getMaterialtypeid() {
		return materialtypeid;
	}

	public void setMaterialtypeid(Integer materialtypeid) {
		this.materialtypeid = materialtypeid;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMaterialtypename() {
		return materialtypename;
	}

	public void setMaterialtypename(String materialtypename) {
		this.materialtypename = materialtypename;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}


	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
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

	public Integer getUpset() {
		return upset;
	}

	public void setUpset(Integer upset) {
		this.upset = upset;
	}

	public Integer getFlowid() {
		return flowid;
	}

	public void setFlowid(Integer flowid) {
		this.flowid = flowid;
	}

	public String getAppnum() {
		return appnum;
	}

	public void setAppnum(String appnum) {
		this.appnum = appnum == null ? null : appnum.trim();
	}

	public Integer getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(Integer approvalstatus) {
		this.approvalstatus = approvalstatus;
	}
}