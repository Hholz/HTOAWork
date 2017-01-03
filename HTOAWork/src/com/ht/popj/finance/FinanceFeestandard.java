package com.ht.popj.finance;

import java.util.Date;

public class FinanceFeestandard {
    private Integer id;

    private String feeName;

    private Float money;

    private String feeremark;

    private String createTime;

    private String updateTime;

    private int status;

    private String remark;

    private FinanceType type;
    
    public FinanceType getType() {
		return type;
	}

	public void setType(FinanceType type) {
		this.type = type;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getFeeremark() {
        return feeremark;
    }

    public void setFeeremark(String feeremark) {
        this.feeremark = feeremark == null ? null : feeremark.trim();
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

	public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}