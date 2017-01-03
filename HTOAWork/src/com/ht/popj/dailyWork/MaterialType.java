package com.ht.popj.dailyWork;

import java.util.Date;

public class MaterialType {
    private Integer id;

    private String materialtypename;

    private String matertypeRemark;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterialtypename() {
        return materialtypename;
    }

    public void setMaterialtypename(String materialtypename) {
        this.materialtypename = materialtypename == null ? null : materialtypename.trim();
    }

    public String getMatertypeRemark() {
        return matertypeRemark;
    }

    public void setMatertypeRemark(String matertypeRemark) {
        this.matertypeRemark = matertypeRemark == null ? null : matertypeRemark.trim();
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
}