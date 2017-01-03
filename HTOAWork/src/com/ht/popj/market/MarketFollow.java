package com.ht.popj.market;

public class MarketFollow {
    private Integer id;

    private Integer stuId;

    private String trackcontent;

    private String tracktime;

    private String trackresult;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private MarketStudent marketstudent;

    public MarketStudent getMarketstudent() {
		return marketstudent;
	}

	public void setMarketstudent(MarketStudent marketstudent) {
		this.marketstudent = marketstudent;
	}

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

    public String getTrackcontent() {
        return trackcontent;
    }

    public void setTrackcontent(String trackcontent) {
        this.trackcontent = trackcontent == null ? null : trackcontent.trim();
    }

    public String getTracktime() {
        return tracktime;
    }

    public void setTracktime(String tracktime) {
        this.tracktime = tracktime;
    }

    public String getTrackresult() {
        return trackresult;
    }

    public void setTrackresult(String trackresult) {
        this.trackresult = trackresult == null ? null : trackresult.trim();
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