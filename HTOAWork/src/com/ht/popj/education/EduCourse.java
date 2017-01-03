package com.ht.popj.education;

public class EduCourse {
    private Integer id;

    private String courseName;

    private String isobligatory;

    private Integer typeId;

    private String courseRemark;

    private String createTime;

    private String updateTime;

    private int status;

    private String remark;

    private EduCourseType type;
    
    public EduCourseType getType() {
		return type;
	}

	public void setType(EduCourseType type) {
		this.type = type;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getIsobligatory() {
        return isobligatory;
    }

    public void setIsobligatory(String isobligatory) {
        this.isobligatory = isobligatory == null ? null : isobligatory.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getCourseRemark() {
        return courseRemark;
    }

    public void setCourseRemark(String courseRemark) {
        this.courseRemark = courseRemark == null ? null : courseRemark.trim();
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
}