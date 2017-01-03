package com.ht.popj.education;

import com.ht.popj.student.StudentFall;

public class EduTerm {
    private Integer id;

    private String termName;

    private Integer fall_id;
    
    private Integer major_id;
    
    private String termRemark;

    private String createTime;

    private String updateTime;

    private int status;

    private String remark;
    
    private String code;

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private StudentFall fall;
    
    private EduMajor major;
    
    private String majorName;
    
    public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public StudentFall getFall() {
		return fall;
	}

	public void setFall(StudentFall fall) {
		this.fall = fall;
	}

	public EduMajor getMajor() {
		return major;
	}

	public void setMajor(EduMajor major) {
		this.major = major;
	}

	public Integer getFall_id() {
		return fall_id;
	}

	public void setFall_id(Integer fall_id) {
		this.fall_id = fall_id;
	}

	public Integer getMajor_id() {
		return major_id;
	}

	public void setMajor_id(Integer major_id) {
		this.major_id = major_id;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName == null ? null : termName.trim();
    }

    public String getTermRemark() {
        return termRemark;
    }

    public void setTermRemark(String termRemark) {
        this.termRemark = termRemark == null ? null : termRemark.trim();
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