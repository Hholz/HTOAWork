package com.ht.popj.education;

import java.util.List;

import com.ht.popj.dailyWork.Emp;

public class EduSeminar {
	private Integer id;

	private String startTime;

	private String endTime;

	private String seminarTheme;

	private String empId;

	private String operatorId;

	private String createTime;

	private String updateTime;

	private Integer status;

	private String remark;

	private Emp operator;

	private Emp sayMan;

	private List<Integer> seminarIds;

	public List<Integer> getSeminarIds() {
		return seminarIds;
	}

	public void setSeminarIds(List<Integer> seminarIds) {
		this.seminarIds = seminarIds;
	}

	public Emp getOperator() {
		return operator;
	}

	public void setOperator(Emp operator) {
		this.operator = operator;
	}

	public Emp getSayMan() {
		return sayMan;
	}

	public void setSayMan(Emp sayMan) {
		this.sayMan = sayMan;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSeminarTheme() {
		return seminarTheme;
	}

	public void setSeminarTheme(String seminarTheme) {
		this.seminarTheme = seminarTheme == null ? null : seminarTheme.trim();
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId == null ? null : empId.trim();
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId == null ? null : operatorId.trim();
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
		return "EduSeminar [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", seminarTheme="
				+ seminarTheme + ", empId=" + empId + ", operatorId=" + operatorId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + ", remark=" + remark + "]";
	}

}