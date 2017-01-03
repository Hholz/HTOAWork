package com.ht.popj.dailyWork;

import java.util.Date;

public class Emp {

	private String id;

    private String empname;

    private Integer depid;

    private Integer fingerprint;

    private String sex;

    private String birthday;

    private String cardno;

    private String nation;

    private String phone;

    private String qqcode;

    private String weixin;

    private String email;

    private String married;

    private String university;

    private String major;

    private String education;

    private String address;

    private String empRemark;

    private String bank;

    private String accountname;

    private String banknumber;

    private String alipay;

    private Date hireday;

    private Date fireday;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String remark;
    
    private Dep dep;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public Integer getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(Integer fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
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

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getQqcode() {
        return qqcode;
    }

    public void setQqcode(String qqcode) {
        this.qqcode = qqcode == null ? null : qqcode.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married == null ? null : married.trim();
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university == null ? null : university.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmpRemark() {
        return empRemark;
    }

    public void setEmpRemark(String empRemark) {
        this.empRemark = empRemark == null ? null : empRemark.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname == null ? null : accountname.trim();
    }

    public String getBanknumber() {
        return banknumber;
    }

    public void setBanknumber(String banknumber) {
        this.banknumber = banknumber == null ? null : banknumber.trim();
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay == null ? null : alipay.trim();
    }

    public Date getHireday() {
        return hireday;
    }

    public void setHireday(Date hireday) {
        this.hireday = hireday;
    }

    public Date getFireday() {
        return fireday;
    }

    public void setFireday(Date fireday) {
        this.fireday = fireday;
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
    
    public Dep getDep() {
		return dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

	@Override
	public String toString() {
		return "Emp [id=" + id + ", empname=" + empname + ", depid=" + depid + ", fingerprint=" + fingerprint + ", sex="
				+ sex + ", birthday=" + birthday + ", cardno=" + cardno + ", nation=" + nation + ", phone=" + phone
				+ ", qqcode=" + qqcode + ", weixin=" + weixin + ", email=" + email + ", married=" + married
				+ ", university=" + university + ", major=" + major + ", education=" + education + ", address="
				+ address + ", empRemark=" + empRemark + ", bank=" + bank + ", accountname=" + accountname
				+ ", banknumber=" + banknumber + ", alipay=" + alipay + ", hireday=" + hireday + ", fireday=" + fireday
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + ", remark="
				+ remark + "]";
	}
}