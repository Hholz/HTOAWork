package com.ht.popj.finance;

public class FinancePayment {
    private Integer id;

    private Integer shouldId;

    private Float payment;

    private Float nonpayment;

    private String remark;
    
    private int status;

    private int classid;
    
    private int termid;
    
    private int stuid;
    
    private Float nonpay;
    
    private Float paid;
    
    private Float money;
    public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Float getPaid() {
		return paid;
	}

	public void setPaid(Float paid) {
		this.paid = paid;
	}

	public Float getNonpay() {
		return nonpay;
	}

	public void setNonpay(Float nonpay) {
		this.nonpay = nonpay;
	}

	private FinanceShouldcharge charge;
    
    public FinanceShouldcharge getCharge() {
		return charge;
	}

	public void setCharge(FinanceShouldcharge charge) {
		this.charge = charge;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public int getTermid() {
		return termid;
	}

	public void setTermid(int termid) {
		this.termid = termid;
	}

	public int getStuid() {
		return stuid;
	}

	public void setStuid(int stuid) {
		this.stuid = stuid;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShouldId() {
        return shouldId;
    }

    public void setShouldId(Integer shouldId) {
        this.shouldId = shouldId;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public Float getNonpayment() {
        return nonpayment;
    }

    public void setNonpayment(Float nonpayment) {
        this.nonpayment = nonpayment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}