package com.ht.popj.sysSet;


import com.ht.popj.dailyWork.Material;
import java.util.Date;

public class FinancePolicquantityset {

	private Integer id;

    private Integer quantity;

    private Integer status;

    private String createTime;

    private String updateTime;

    private String remark;

    private Integer materialid;
    
    private String materialname;
    
    private Material material;
    
    private Integer countstatus;
    
    private Integer materialtypeid;
    
    private String materialtypename;
    
    private Integer lessnumber;

    public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
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

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

	public Integer getCountstatus() {
		return countstatus;
	}

	public void setCountstatus(Integer countstatus) {
		this.countstatus = countstatus;
	}

	public String getMaterialtypename() {
		return materialtypename;
	}

	public void setMaterialtypename(String materialtypename) {
		this.materialtypename = materialtypename;
	}

	public Integer getMaterialtypeid() {
		return materialtypeid;
	}

	public void setMaterialtypeid(Integer materialtypeid) {
		this.materialtypeid = materialtypeid;
	}

	public Integer getLessnumber() {
		return lessnumber;
	}

	public void setLessnumber(Integer lessnumber) {
		this.lessnumber = lessnumber;
	}

	@Override
	public String toString() {
		return "FinancePolicquantityset [id=" + id + ", quantity=" + quantity + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", remark=" + remark + ", materialid=" + materialid
				+ ", materialname=" + materialname + ", material=" + material + ", countstatus=" + countstatus
				+ ", materialtypeid=" + materialtypeid + ", materialtypename=" + materialtypename + ", lessnumber="
				+ lessnumber + "]";
	}





	
}