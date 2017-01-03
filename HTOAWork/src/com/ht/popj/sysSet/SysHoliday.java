package com.ht.popj.sysSet;

import java.util.Date;

public class SysHoliday {
    private Integer id;

    private String holidaytime;

    private Integer holidaystat;

    private String remark;

    private Integer status;

    private String updateholiday;
    
    private String endtime;
    
    

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public Integer getHolidaystat() {
        return holidaystat;
    }

    public void setHolidaystat(Integer holidaystat) {
        this.holidaystat = holidaystat;
    }

    public String getRemark() {
        return remark;
    }

    public String getHolidaytime() {
    	
		return holidaytime;
	}

	public void setHolidaytime(String holidaytime) {
		this.holidaytime = holidaytime;
	}

	public String getUpdateholiday() {
		return updateholiday;
	}

	public void setUpdateholiday(String updateholiday) {
		this.updateholiday = updateholiday;
	}

	public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    
}