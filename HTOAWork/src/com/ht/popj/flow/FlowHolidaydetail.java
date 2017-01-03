package com.ht.popj.flow;

import com.ht.popj.dailyWork.Emp;

public class FlowHolidaydetail {
	private Integer id;

	private Integer holidayid;

	private Integer flowmodelid;

	private String empid;

	private Integer status;

	private String remark;

	private FlowHoliday flowHoliday;

	private Emp emp;

	private FlowsModel flowModel;

	public FlowsModel getFlowModel() {
		return flowModel;
	}

	public void setFlowModel(FlowsModel flowModel) {
		this.flowModel = flowModel;
	}

	public FlowHoliday getFlowHoliday() {
		return flowHoliday;
	}

	public void setFlowHoliday(FlowHoliday flowHoliday) {
		this.flowHoliday = flowHoliday;
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

	public Integer getHolidayid() {
		return holidayid;
	}

	public void setHolidayid(Integer holidayid) {
		this.holidayid = holidayid;
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

	@Override
	public String toString() {
		return "FlowHolidaydetail [id=" + id + ", holidayid=" + holidayid + ", flowmodelid=" + flowmodelid + ", empid="
				+ empid + ", status=" + status + ", remark=" + remark + "]";
	}

}