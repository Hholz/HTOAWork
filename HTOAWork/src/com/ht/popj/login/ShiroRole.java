package com.ht.popj.login;

import java.util.Date;
import java.util.List;

public class ShiroRole {
    private Integer id;

    private String name;

    private String roleDesc;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;
    
    private List<ShiroAuth> authList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
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

	public List<ShiroAuth> getAuthList() {
		return authList;
	}

	public void setAuthList(List<ShiroAuth> authList) {
		this.authList = authList;
	}
}