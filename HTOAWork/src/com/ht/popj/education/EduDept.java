package com.ht.popj.education;

import java.util.List;

public class EduDept {
    private Integer id;

    private String deptName;

    private String deptRemark;

    private String createTime;

    private String updateTime;

    private int status;

    private String remark;

    public List<EduMajor> getMajorList() {
		return majorList;
	}

	public void setMajorList(List<EduMajor> majorList) {
		this.majorList = majorList;
	}

	private List<EduMajor> majorList;//“ª∂‘∂‡≈‰÷√
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark == null ? null : deptRemark.trim();
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

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}