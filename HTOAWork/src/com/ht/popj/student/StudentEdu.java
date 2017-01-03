package com.ht.popj.student;

import java.util.Date;

public class StudentEdu {
    private Integer id;

    private Integer studentId;

    private String school;

    private String begindate;

    private String enddate;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String remark;
    
    private Student student;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
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

	public String getBegindate() {
		if(null!=begindate&&!begindate.isEmpty()){
			return begindate.substring(0, 10);
		}else{
			return begindate;
		}
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		if(null!=enddate&&!enddate.isEmpty()){
			return enddate.substring(0, 10);
		}else{
			return enddate;
		}
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}