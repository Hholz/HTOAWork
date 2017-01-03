package com.ht.popj.market;

import java.util.Date;

import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentHourse;

public class PredestinationStudent {
    private Integer id;

    private String name;

    private String sex;

    private Integer age;

    private String telephone;

    private String adress;

    private Integer classid;

    private Integer houseid;

    private Integer predStatus;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private StudentClass stuclass;
    
    private StudentHourse house;

    public StudentHourse getHouse() {
		return house;
	}

	public void setHouse(StudentHourse house) {
		this.house = house;
	}

	public StudentClass getStuclass() {
		return stuclass;
	}

	public void setStuclass(StudentClass stuclass) {
		this.stuclass = stuclass;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress == null ? null : adress.trim();
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getHouseid() {
        return houseid;
    }

    public void setHouseid(Integer houseid) {
        this.houseid = houseid;
    }

    public Integer getPredStatus() {
        return predStatus;
    }

    public void setPredStatus(Integer predStatus) {
        this.predStatus = predStatus;
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