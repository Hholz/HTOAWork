package com.ht.popj.dailyWork;

/*
 * 查询一周中,某值班类型 最大的是有多少个
 */
public class DutyMaxTemp {

	private int empType;//值班人类型     值班老师/值班班主任/总值班（1，2，3）
	private int theMax;//该周该值班人类型最大有多少个
	public int getEmpType() {
		return empType;
	}
	public void setEmpType(int empType) {
		this.empType = empType;
	}
	public int getTheMax() {
		return theMax;
	}
	public void setTheMax(int theMax) {
		this.theMax = theMax;
	}
}
