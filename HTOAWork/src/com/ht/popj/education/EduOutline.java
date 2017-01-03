package com.ht.popj.education;


public class EduOutline {
    private Integer id;

    private Integer courseId;

    private String outlineName;

    private String outlineContent;

    private Integer hour;

    private String outlineRemark;

    private String createTime;

    private String updateTime;

    private int status;

    private String remark;

    private EduCourse course;
    
    public EduCourse getCourse() {
		return course;
	}

	public void setCourse(EduCourse course) {
		this.course = course;
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

    public String getOutlineName() {
        return outlineName;
    }

    public void setOutlineName(String outlineName) {
        this.outlineName = outlineName == null ? null : outlineName.trim();
    }

    public String getOutlineContent() {
        return outlineContent;
    }

    public void setOutlineContent(String outlineContent) {
        this.outlineContent = outlineContent == null ? null : outlineContent.trim();
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getOutlineRemark() {
        return outlineRemark;
    }

    public void setOutlineRemark(String outlineRemark) {
        this.outlineRemark = outlineRemark == null ? null : outlineRemark.trim();
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

    @Override
	public String toString() {
		return "EduOutline [id=" + id + ", courseId=" + courseId + ", outlineName=" + outlineName + ", outlineContent="
				+ outlineContent + ", hour=" + hour + ", outlineRemark=" + outlineRemark + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + ", remark=" + remark + ", course=" + course
				+ "]";
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
}