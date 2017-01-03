package com.ht.popj.finance;


public class FinanceSetList {
    private Integer id;

    private String empid;
    
    private String empname;
    
    private Float jbmoney;
    
    private Float gwmoney;
    
    private Float sbmoney;
    
    private Float ybmoney;
    
    private Float cbmoney;
    
    private Float zfmoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Float getJbmoney() {
		return jbmoney;
	}

	public void setJbmoney(Float jbmoney) {
		this.jbmoney = jbmoney;
	}

	public Float getGwmoney() {
		return gwmoney;
	}

	public void setGwmoney(Float gwmoney) {
		this.gwmoney = gwmoney;
	}

	public Float getSbmoney() {
		return sbmoney;
	}

	public void setSbmoney(Float sbmoney) {
		this.sbmoney = sbmoney;
	}

	public Float getYbmoney() {
		return ybmoney;
	}

	public void setYbmoney(Float ybmoney) {
		this.ybmoney = ybmoney;
	}

	public Float getCbmoney() {
		return cbmoney;
	}

	public void setCbmoney(Float cbmoney) {
		this.cbmoney = cbmoney;
	}

	public Float getZfmoney() {
		return zfmoney;
	}

	public void setZfmoney(Float zfmoney) {
		this.zfmoney = zfmoney;
	}
    
    
}