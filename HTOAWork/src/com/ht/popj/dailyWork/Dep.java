package com.ht.popj.dailyWork;

import java.util.Date;
import java.util.List;

public class Dep {

	private Integer id;

    private String depname;

    private Integer parentid;

    private String chairman;

    private String depRemark;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String remark;
    
    private List<Dep> childendep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname == null ? null : depname.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getChairman() {
        return chairman;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman == null ? null : chairman.trim();
    }

    public String getDepRemark() {
        return depRemark;
    }

    public void setDepRemark(String depRemark) {
        this.depRemark = depRemark == null ? null : depRemark.trim();
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

	public List<Dep> getChildendep() {
		return childendep;
	}

	public void setChildendep(List<Dep> childendep) {
		this.childendep = childendep;
	}
    @Override
	public String toString() {
		return "Dep [id=" + id + ", depname=" + depname + ", parentid=" + parentid + ", chairman=" + chairman
				+ ", depRemark=" + depRemark + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status="
				+ status + ", remark=" + remark + ", childendep=" + childendep + "]";
	}
}