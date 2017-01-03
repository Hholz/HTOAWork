package com.ht.popj.finance;

import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;

public class FinanceTuitionset {
    private Integer id;

    private Integer termid;

    private Integer tuition;

    private Integer status;

    private String createTime;

    private String updateTime;

    private String remark;
    
    private EduTerm eduterm;
	public EduTerm getEduterm() {
		return eduterm;
	}

	public void setEduterm(EduTerm eduterm) {
		this.eduterm = eduterm;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTermid() {
		return termid;
	}

	public void setTermid(Integer termid) {
		this.termid = termid;
	}

	public Integer getTuition() {
        return tuition;
    }

    public void setTuition(Integer tuition) {
        this.tuition = tuition;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}