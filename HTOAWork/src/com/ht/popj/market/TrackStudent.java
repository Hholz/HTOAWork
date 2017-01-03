package com.ht.popj.market;

import com.ht.popj.dailyWork.Emp;

public class TrackStudent {
    private Integer id;

    private Integer studid;

    private Integer trackways;

    private String trackcontent;

    private String tracktime;

    private String trackresult;

    private String empid;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private IntentionStudent intenstudent;
    
    private TrackStudent trackstudent;
    
    public TrackStudent getTrackstudent() {
		return trackstudent;
	}

	public void setTrackstudent(TrackStudent trackstudent) {
		this.trackstudent = trackstudent;
	}

	private Emp emp;
    
    public IntentionStudent getIntenstudent() {
		return intenstudent;
	}

	public void setIntenstudent(IntentionStudent intenstudent) {
		this.intenstudent = intenstudent;
	}

    public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudid() {
        return studid;
    }

    public void setStudid(Integer studid) {
        this.studid = studid;
    }

    public Integer getTrackways() {
        return trackways;
    }

    public void setTrackways(Integer trackways) {
        this.trackways = trackways;
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

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
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