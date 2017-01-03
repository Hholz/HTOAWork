package com.ht.popj.dailyWork;

import java.util.Date;

public class Familyinfo {
    private Integer id;

    private String empid;

    private String contactname;

    private String relationship;

    private String phone;

    private String familyRemark;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String remark;
    
    private Emp emp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname == null ? null : contactname.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFamilyRemark() {
        return familyRemark;
    }

    public void setFamilyRemark(String familyRemark) {
        this.familyRemark = familyRemark == null ? null : familyRemark.trim();
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

    public Byte getStatus() {
        return status;
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

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}
    
	@Override
	public String toString() {
		return "Familyinfo [id=" + id + ", empid=" + empid + ", contactname=" + contactname + ", relationship="
				+ relationship + ", phone=" + phone + ", familyRemark=" + familyRemark + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + ", remark=" + remark + "]";
	}
	
}