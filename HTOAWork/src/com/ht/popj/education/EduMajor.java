package com.ht.popj.education;


public class EduMajor {
    private Integer id;

    private Integer deptId;

    private String majorName;

    private String majorRemark;

    private String createTime;

    private String updateTime;

    private String code;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private int status;

    private String remark;

    private EduDept dept;
    
    
    public EduDept getDept() {
		return dept;
	}

	public void setDept(EduDept dept) {
		this.dept = dept;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName == null ? null : majorName.trim();
    }

    public String getMajorRemark() {
        return majorRemark;
    }

    public void setMajorRemark(String majorRemark) {
        this.majorRemark = majorRemark == null ? null : majorRemark.trim();
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

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}