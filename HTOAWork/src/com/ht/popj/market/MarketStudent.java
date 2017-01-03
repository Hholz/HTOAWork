package com.ht.popj.market;

import java.util.Date;

import com.ht.popj.dailyWork.Emp;

public class MarketStudent {
    private Integer id;

    private String name;

    private String sex;

    private Integer age;

    private String phone;

    private String addr;
    
    private String school;

    private String empId;

    private Integer msStatus;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String qqCode;
    private String idCard;
    private String clsName;//原来学校名称
    private String applyCost;//是否交报名费
    private String iGrade;//意向等级
    private String remark;
    private Integer huorid;//宿舍id
    private Integer classid;//班级id
    private String isTest;//是否试学
    //招生员工信息
    private Emp recruitEmp;
    //跟踪记录条数
    private int followCount;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public Integer getMsStatus() {
        return msStatus;
    }

    public void setMsStatus(Integer msStatus) {
        this.msStatus = msStatus;
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

	public Emp getRecruitEmp() {
		return recruitEmp;
	}

	public void setRecruitEmp(Emp recruitEmp) {
		this.recruitEmp = recruitEmp;
	}

	public int getFollowCount() {
		return followCount;
	}

	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}

	public String getQqCode() {
		return qqCode;
	}

	public void setQqCode(String qqCode) {
		this.qqCode = qqCode;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public String getApplyCost() {
		return applyCost;
	}

	public void setApplyCost(String applyCost) {
		this.applyCost = applyCost;
	}

	public String getiGrade() {
		return iGrade;
	}

	public void setiGrade(String iGrade) {
		this.iGrade = iGrade;
	}

	public Integer getHuorid() {
		return huorid;
	}

	public void setHuorid(Integer huorid) {
		this.huorid = huorid;
	}

	public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	public String getIsTest() {
		return isTest;
	}

	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
}