package com.ht.popj.student;

import java.util.Date;
import java.util.List;

public class StudentFall {
    private Integer id;

    private String level;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private List<StudentClass> studentclass;
    
    public List<StudentClass> getStudentclass() {
		return studentclass;
	}

	public void setStudentclass(List<StudentClass> studentclass) {
		this.studentclass = studentclass;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
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
}