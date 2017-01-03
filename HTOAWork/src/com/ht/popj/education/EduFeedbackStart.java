package com.ht.popj.education;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.StudentClass;

public class EduFeedbackStart {
	private Integer id;

	private String startTime;

	private String endTime;

	private String feedbackStatus;

	private Integer classId;

	private String empId;

	private String startEmpid;

	private String createTime;

	private String updateTime;

	private Emp empStart;
	
	private Emp empBeStart;

	private StudentClass stuClass;
	public String getStartTime() {
		return startTime;
	}

	public StudentClass getStuClass() {
		return stuClass;
	}

	public void setStuClass(StudentClass stuClass) {
		this.stuClass = stuClass;
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

	private Integer status;

	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus == null ? null : feedbackStatus.trim();
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId == null ? null : empId.trim();
	}

	public String getStartEmpid() {
		return startEmpid;
	}

	public void setStartEmpid(String startEmpid) {
		this.startEmpid = startEmpid == null ? null : startEmpid.trim();
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
	

	public Emp getEmpStart() {
		return empStart;
	}

	public void setEmpStart(Emp empStart) {
		this.empStart = empStart;
	}

	public Emp getEmpBeStart() {
		return empBeStart;
	}

	public void setEmpBeStart(Emp empBeStart) {
		this.empBeStart = empBeStart;
	}

	@Override
	public String toString() {
		return "EduFeedbackStart [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", feedbackStatus="
				+ feedbackStatus + ", classId=" + classId + ", empId=" + empId + ", startEmpid=" + startEmpid
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + ", remark="
				+ remark + "]";
	}

}