package com.ht.popj.student;

import java.util.Date;
import java.util.List;

public class StudentLayer {
    private Integer id;

    private String layername;

    private Integer floorid;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private StudentFloor studentFloor;
  

	public StudentFloor getStudentFloor() {
		return studentFloor;
	}

	public void setStudentFloor(StudentFloor studentFloor) {
		this.studentFloor = studentFloor;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLayername() {
        return layername;
    }

    public void setLayername(String layername) {
        this.layername = layername == null ? null : layername.trim();
    }

    public Integer getFloorid() {
        return floorid;
    }

    public void setFloorid(Integer floorid) {
        this.floorid = floorid;
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