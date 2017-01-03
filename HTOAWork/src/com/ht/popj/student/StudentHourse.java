package com.ht.popj.student;


public class StudentHourse {
    private Integer id;

    private String hoursename;

    private String addr;

    private Integer count;

    private Integer layerid;

    private Integer floorid;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private String hsex;
    
    public String getHsex() {
		return hsex;
	}

	public void setHsex(String hsex) {
		this.hsex = hsex;
	}

	private Integer currentCount;

    private StudentFloor floor;
    private StudentLayer layer;
    public StudentFloor getFloor() {
		return floor;
	}

	public void setFloor(StudentFloor floor) {
		this.floor = floor;
	}

	public StudentLayer getLayer() {
		return layer;
	}

	public void setLayer(StudentLayer layer) {
		this.layer = layer;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHoursename() {
        return hoursename;
    }

    public void setHoursename(String hoursename) {
        this.hoursename = hoursename == null ? null : hoursename.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getLayerid() {
        return layerid;
    }

    public void setLayerid(Integer layerid) {
        this.layerid = layerid;
    }

    public Integer getFloorid() {
        return floorid;
    }

    public void setFloorid(Integer floorid) {
        this.floorid = floorid;
    }

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
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