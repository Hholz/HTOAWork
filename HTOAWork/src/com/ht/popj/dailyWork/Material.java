package com.ht.popj.dailyWork;

import java.math.BigDecimal;
import java.util.Date;

public class Material {
    private Integer id;

    private Integer materialtypeid;
    
    private String typename;

    private String materialname;

    private String unit;

    private BigDecimal price;

    private String style;

    private Integer counts;

    private String meterialRemark;

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

    public Integer getMaterialtypeid() {
        return materialtypeid;
    }

    public void setMaterialtypeid(Integer materialtypeid) {
        this.materialtypeid = materialtypeid;
    }

    public String getMaterialname() {
        return materialname;
    }

    public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public void setMaterialname(String materialname) {
        this.materialname = materialname == null ? null : materialname.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style == null ? null : style.trim();
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getMeterialRemark() {
        return meterialRemark;
    }

    public void setMeterialRemark(String meterialRemark) {
        this.meterialRemark = meterialRemark == null ? null : meterialRemark.trim();
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