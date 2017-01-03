package com.ht.popj.student;

import java.util.Date;

public class StudentAttence {
    private Integer id;

    private Integer stuId;

    private Integer clsId;
    
    private String clsName;

    private String forenoon;

    private String morning;

    private String afternoon;

    private String night;

    private String descs;

    private String createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private Student stu;
    
    private StudentClass cls;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getClsId() {
        return clsId;
    }

    public void setClsId(Integer clsId) {
        this.clsId = clsId;
    }

    public String getForenoon() {
        return forenoon;
    }

    public void setForenoon(String forenoon) {
        this.forenoon = forenoon == null ? null : forenoon.trim();
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning == null ? null : morning.trim();
    }

    public String getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(String afternoon) {
        this.afternoon = afternoon == null ? null : afternoon.trim();
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night == null ? null : night.trim();
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs == null ? null : descs.trim();
    }

    public String getCreateTime() {
    	if(null!=createTime&&!createTime.isEmpty()){
			return createTime.substring(0, 10);
		}else{
			return createTime;
		}
    }

    public void setCreateTime(String createTime) {
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

	public StudentClass getCls() {
		return cls;
	}

	public void setCls(StudentClass cls) {
		this.cls = cls;
	}

	public Student getStu() {
		return stu;
	}

	public void setStu(Student stu) {
		this.stu = stu;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
}