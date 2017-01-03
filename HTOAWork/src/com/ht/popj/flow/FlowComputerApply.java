package com.ht.popj.flow;

import java.util.Date;

import com.ht.popj.student.Student;

public class FlowComputerApply {
    private Integer id;

    private Integer studid;

    private Date createTime;

    private Date updateTime;

    private String computerapply;

    private Integer status;

    private String empid;

    private String remark;
    
    private Integer classid;
    
    private Integer fallid;
    
    private Student student;
    
    private Integer materialid;

    public Integer getMaterialid() {
		return materialid;
	}

	public void setMaterialid(Integer materialid) {
		this.materialid = materialid;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	public Integer getFallid() {
		return fallid;
	}

	public void setFallid(Integer fallid) {
		this.fallid = fallid;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudid() {
        return studid;
    }

    public void setStudid(Integer studid) {
        this.studid = studid;
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

    public String getComputerapply() {
        return computerapply;
    }

    public void setComputerapply(String computerapply) {
        this.computerapply = computerapply == null ? null : computerapply.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}