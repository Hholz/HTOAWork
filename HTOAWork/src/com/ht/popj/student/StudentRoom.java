package com.ht.popj.student;

public class StudentRoom {
    private Integer id;

	private Integer hourseid;

	private String studentid;

	private String createtime;

	private String updatetime;

	private Integer status;

	private String remark;
	
	private StudentHourse hourse;
	
	private Student student;

	private String classname;
	public StudentHourse getHourse() {
		return hourse;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public void setHourse(StudentHourse hourse) {
		this.hourse = hourse;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHourseid() {
		return hourseid;
	}

	public void setHourseid(Integer hourseid) {
		this.hourseid = hourseid;
	}

	public Integer getStatus() {
		return status;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}