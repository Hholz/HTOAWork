package com.ht.popj.student;

import java.util.Date;

public class StudentFloor {
    private Integer id;

    private String floorname;

    private Date createTime;

    private Date updateTime;

    private Integer floorStatus;

    private String remark;

    private String floorAdmin;
    
    private String phone;
    
    private String address;

    private int layernum;
    
	public int getLayernum() {
		return layernum;
	}

	public void setLayernum(int layernum) {
		this.layernum = layernum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFloorname() {
        return floorname;
    }

    public void setFloorname(String floorname) {
        this.floorname = floorname == null ? null : floorname.trim();
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

    public Integer getFloorStatus() {
        return floorStatus;
    }

    public void setFloorStatus(Integer floorStatus) {
        this.floorStatus = floorStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFloorAdmin() {
        return floorAdmin;
    }

    public void setFloorAdmin(String floorAdmin) {
        this.floorAdmin = floorAdmin == null ? null : floorAdmin.trim();
    }
}