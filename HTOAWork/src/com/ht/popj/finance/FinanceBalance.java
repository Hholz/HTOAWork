package com.ht.popj.finance;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.Student;

public class FinanceBalance {
    private Integer id;

    private Integer typeId;

    private String occurtime;

    private Float money;
    
    private Float payment;

    private String financeman;

    private int studId;
    
    private String orderId;

	private String reportman;

    private String systime;

    private String financeRemark;

    private String createTime;

    private String updateTime;

    private int status;

    private String remark;
    
    private Emp reportEmp;
    
    private Emp financeEmp;
    
    private FinanceType type;

	private Student stud;
	
    private FinanceFeestandard fee;
    
    private String startTime;
    
    private String endTime;
    
    private String identifying;
    
    private Double allin;

	private Double allout;

	private double typeIn;

	private double typeOut;

	private String typeOutName;

	private String typeInName;
	
	public Float getPayment() {
		return payment;
	}

	public void setPayment(Float payment) {
		this.payment = payment;
	}

	public String getIdentifying() {
		return identifying;
	}

	public void setIdentifying(String identifying) {
		this.identifying = identifying;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public FinanceFeestandard getFee() {
		return fee;
	}

	public void setFee(FinanceFeestandard fee) {
		this.fee = fee;
	}

	public Student getStud() {
		return stud;
	}

	public void setStud(Student stud) {
		this.stud = stud;
	}

	public Emp getReportEmp() {
		return reportEmp;
	}

	public void setReportEmp(Emp reportEmp) {
		this.reportEmp = reportEmp;
	}

    public int getStudId() {
		return studId;
	}

	public void setStudId(int studId) {
		this.studId = studId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Emp getFinanceEmp() {
		return financeEmp;
	}

	public void setFinanceEmp(Emp financeEmp) {
		this.financeEmp = financeEmp;
	}

	public FinanceType getType() {
		return type;
	}

	public void setType(FinanceType type) {
		this.type = type;
	}
	
	public Double getAllin() {
		return allin;
	}

	public void setAllin(Double allin) {
		this.allin = allin;
	}

	public Double getAllout() {
		return allout;
	}

	public void setAllout(Double allout) {
		this.allout = allout;
	}

	public double getTypeIn() {
		return typeIn;
	}

	public void setTypeIn(double typeIn) {
		this.typeIn = typeIn;
	}

	public double getTypeOut() {
		return typeOut;
	}

	public void setTypeOut(double typeOut) {
		this.typeOut = typeOut;
	}

	public String getTypeOutName() {
		return typeOutName;
	}

	public void setTypeOutName(String typeOutName) {
		this.typeOutName = typeOutName;
	}

	public String getTypeInName() {
		return typeInName;
	}

	public void setTypeInName(String typeInName) {
		this.typeInName = typeInName;
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getFinanceman() {
		return financeman;
	}

	public void setFinanceman(String financeman) {
		this.financeman = financeman;
	}

	public String getReportman() {
		return reportman;
	}

	public void setReportman(String reportman) {
		this.reportman = reportman;
	}

	public String getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(String occurtime) {
		this.occurtime = occurtime;
	}

	public String getSystime() {
		return systime;
	}

	public void setSystime(String systime) {
		this.systime = systime;
	}

	public String getFinanceRemark() {
        return financeRemark;
    }

    public void setFinanceRemark(String financeRemark) {
        this.financeRemark = financeRemark == null ? null : financeRemark.trim();
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

	@Override
	public String toString() {
		return "FinanceBalance [id=" + id + ", typeId=" + typeId + ", occurtime=" + occurtime + ", money=" + money
				+ ", payment=" + payment + ", financeman=" + financeman + ", studId=" + studId + ", orderId=" + orderId
				+ ", reportman=" + reportman + ", systime=" + systime + ", financeRemark=" + financeRemark
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + ", remark="
				+ remark + ", reportEmp=" + reportEmp + ", financeEmp=" + financeEmp + ", type=" + type + ", stud="
				+ stud + ", fee=" + fee + ", startTime=" + startTime + ", endTime=" + endTime + ", identifying="
				+ identifying + ", allin=" + allin + ", allout=" + allout + ", typeIn=" + typeIn + ", typeOut="
				+ typeOut + ", typeOutName=" + typeOutName + ", typeInName=" + typeInName + "]";
	}
    
}