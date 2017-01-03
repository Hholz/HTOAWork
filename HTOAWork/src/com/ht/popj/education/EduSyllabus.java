package com.ht.popj.education;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.StudentClass;

public class EduSyllabus {
    private Integer id;

    private Integer courseId;
    private Integer outlineId;

    private String startTime;

    private String endTime;

    private Integer hour;

    private Integer classId;

    private String empId;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;

    private EduOutline outline;
    
    private StudentClass stuClass;
    
    private Emp empTeacher;
    
    private EduCourse course;
    
    private Integer allhour;
    
    private Integer plan;
    
    private Integer today;
    
    private Integer finishhour;
    
    
    
	public Integer getAllhour() {
		return allhour;
	}

	public void setAllhour(Integer allhour) {
		this.allhour = allhour;
	}

	public Integer getPlan() {
		return plan;
	}

	public void setPlan(Integer plan) {
		this.plan = plan;
	}

	public Integer getToday() {
		return today;
	}

	public void setToday(Integer today) {
		this.today = today;
	}

	public Integer getFinishhour() {
		return finishhour;
	}

	public void setFinishhour(Integer finishhour) {
		this.finishhour = finishhour;
	}

	public EduCourse getCourse() {
		return course;
	}

	public void setCourse(EduCourse course) {
		this.course = course;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
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

	public EduOutline getOutline() {
		return outline;
	}

	public void setOutline(EduOutline outline) {
		this.outline = outline;
	}

	public StudentClass getStuClass() {
		return stuClass;
	}

	public void setStuClass(StudentClass stuClass) {
		this.stuClass = stuClass;
	}

	public Emp getEmpTeacher() {
		return empTeacher;
	}

	public void setEmpTeacher(Emp empTeacher) {
		this.empTeacher = empTeacher;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOutlineId() {
        return outlineId;
    }

    public void setOutlineId(Integer outlineId) {
        this.outlineId = outlineId;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
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
		return "EduSyllabus [id=" + id + ", courseId=" + courseId + ", outlineId=" + outlineId + ", startTime="
				+ startTime + ", endTime=" + endTime + ", hour=" + hour + ", classId=" + classId + ", empId=" + empId
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + ", remark="
				+ remark + ", outline=" + outline + ", stuClass=" + stuClass + ", empTeacher=" + empTeacher
				+ ", course=" + course + ", allhour=" + allhour + ", plan=" + plan + ", today=" + today
				+ ", finishhour=" + finishhour + "]";
	}
    
}