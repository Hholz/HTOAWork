package com.ht.popj.student;

import java.util.Date;

import com.ht.popj.education.EduMajor;
import com.ht.popj.sysSet.Residence;

public class Student {
    private Integer id;

    private String stuno;

    private String password;

    private String stuname;

    private String middleschool;

    private String sex;

    private Integer age;

    private String birthday;

    private String phone;

    private String addr;

    private Integer classid;

    private Integer huorid;

    private String entertime;

    private String introduretech;

    private Integer stuStatus;

    private String nation;

    private String naplace;

    private Integer residence;

    private String idcard;

    private String professional;

    private Integer prolevel;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private Residence resPJ;
    
    private StudentClass classInfo;
    
    private StudentHourse hourse;
    
    private EduMajor major;
    
    private int Num;
    public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}

	public Integer getId() {
        return id;
    }

	public void setId(Integer id) {
        this.id = id;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno == null ? null : stuno.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname == null ? null : stuname.trim();
    }

    public String getMiddleschool() {
        return middleschool;
    }

    public void setMiddleschool(String middleschool) {
        this.middleschool = middleschool == null ? null : middleschool.trim();
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

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getHuorid() {
        return huorid;
    }

    public void setHuorid(Integer huorid) {
        this.huorid = huorid;
    }

    public String getIntroduretech() {
        return introduretech;
    }

    public void setIntroduretech(String introduretech) {
        this.introduretech = introduretech == null ? null : introduretech.trim();
    }

    public Integer getStuStatus() {
        return stuStatus;
    }

    public void setStuStatus(Integer stuStatus) {
        this.stuStatus = stuStatus;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getNaplace() {
        return naplace;
    }

    public void setNaplace(String naplace) {
        this.naplace = naplace == null ? null : naplace.trim();
    }

    public Integer getResidence() {
        return residence;
    }

    public void setResidence(Integer residence) {
        this.residence = residence;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional == null ? null : professional.trim();
    }

    public Integer getProlevel() {
        return prolevel;
    }

    public void setProlevel(Integer prolevel) {
        this.prolevel = prolevel;
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

	public String getBirthday() {
		if(null!=birthday&&!birthday.isEmpty()){
			return birthday.substring(0, 10);
		}else{
			return birthday;
		}
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEntertime() {
		if(null!=entertime&&!entertime.isEmpty()){
			return entertime.substring(0, 10);
		}else{
			return entertime;
		}
	}

	public void setEntertime(String entertime) {
		this.entertime = entertime;
	}

	public Residence getResPJ() {
		return resPJ;
	}

	public void setResPJ(Residence resPJ) {
		this.resPJ = resPJ;
	}

	public StudentClass getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(StudentClass classInfo) {
		this.classInfo = classInfo;
	}

	public StudentHourse getHourse() {
		return hourse;
	}

	public void setHourse(StudentHourse hourse) {
		this.hourse = hourse;
	}

	public EduMajor getMajor() {
		return major;
	}

	public void setMajor(EduMajor major) {
		this.major = major;
	}
}