package com.ht.popj.education;


import com.ht.popj.dailyWork.Emp;

public class EduCourseTeacher {
    private Integer id;

    private Integer courseId;

	private String empId;

    private String createTime;

    private String updateTime;

    private int status;

    private String remark;

    private EduCourse course;
    
    private Emp emp;
    
    public EduCourse getCourse() {
		return course;
	}

	public void setCourse(EduCourse course) {
		this.course = course;
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
    
    @Override
  	public String toString() {
  		return "EduCourseTeacher [id=" + id + ", courseId=" + courseId + ", empId=" + empId + ", createTime="
  				+ createTime + ", updateTime=" + updateTime + ", status=" + status + ", remark=" + remark + ", course="
  				+ course + ", emp=" + emp + "]";
  	}

}