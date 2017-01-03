package com.ht.popj.finance;

import java.util.Date;

public class FinanceShouldcharge {
    private Integer id;

    private Integer stuid;

    private Integer classid;

    private Integer termid;

    private Integer fallid;
    
    private Integer majorid;

    private Float money;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private int ispay;
    
    private String stuname;
    
    private String level;
    
    private String termname;
    
    private String majorName;
    
    private Float payment;

    private String classname;

	public Integer getMajorid() {
		return majorid;
	}

	public void setMajorid(Integer majorid) {
		this.majorid = majorid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getTermid() {
        return termid;
    }

    public void setTermid(Integer termid) {
        this.termid = termid;
    }


    public Integer getFallid() {
		return fallid;
	}

	public void setFallid(Integer fallid) {
		this.fallid = fallid;
	}

    public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Float getPayment() {
		return payment;
	}

	public void setPayment(Float payment) {
		this.payment = payment;
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

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getTermname() {
		return termname;
	}

	public void setTermname(String termname) {
		this.termname = termname;
	}

	public int getIspay() {
		return ispay;
	}

	public void setIspay(int ispay) {
		this.ispay = ispay;
	}

	@Override
	public String toString() {
		return "FinanceShouldcharge [id=" + id + ", stuid=" + stuid + ", classid=" + classid + ", termid=" + termid
				+ ", fallid=" + fallid + ", money=" + money + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", status=" + status + ", remark=" + remark + ", ispay=" + ispay + ", stuname=" + stuname
				+ ", level=" + level + ", termname=" + termname +"]";
	}

}