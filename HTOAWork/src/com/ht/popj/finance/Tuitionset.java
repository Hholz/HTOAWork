package com.ht.popj.finance;

import com.ht.popj.education.EduDept;
import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;
import com.ht.popj.student.StudentFall;

public class Tuitionset {
    private Integer id;

    private Integer fallid;

    private Integer term;

    private Integer edudeptid;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;
    
    private StudentFall fall;
    
    private EduMajor major;
    
    private EduTerm termname;
    
    private Integer sum;
    
    private String term_Name;
    
    public String getTerm_Name() {
		return term_Name;
	}

	public void setTerm_Name(String term_Name) {
		this.term_Name = term_Name;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

    public EduTerm getTermname() {
		return termname;
	}

	public void setTermname(EduTerm termname) {
		this.termname = termname;
	}

	
	public EduMajor getMajor() {
		return major;
	}

	public void setMajor(EduMajor major) {
		this.major = major;
	}

	public StudentFall getFall() {
		return fall;
	}

	public void setFall(StudentFall fall) {
		this.fall = fall;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFallid() {
        return fallid;
    }

    public void setFallid(Integer fallid) {
        this.fallid = fallid;
    }

    public Integer getEdudeptid() {
        return edudeptid;
    }

    public void setEdudeptid(Integer edudeptid) {
        this.edudeptid = edudeptid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
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