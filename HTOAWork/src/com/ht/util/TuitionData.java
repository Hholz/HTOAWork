package com.ht.util;

public class TuitionData {
	
	private float sumPay;//已交总金额
	private float sumNonpay;//未交总金额
	private int countPay;//已付总人数
	private int countNonpay;//未付总人数
	private float money;//应付资金
	public float getSumPay() {
		return sumPay;
	}
	public void setSumPay(float sumPay) {
		this.sumPay = sumPay;
	}
	public float getSumNonpay() {
		return sumNonpay;
	}
	public void setSumNonpay(float sumNonpay) {
		this.sumNonpay = sumNonpay;
	}
	public int getCountPay() {
		return countPay;
	}
	public void setCountPay(int countPay) {
		this.countPay = countPay;
	}
	public int getCountNonpay() {
		return countNonpay;
	}
	public void setCountNonpay(int countNonpay) {
		this.countNonpay = countNonpay;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
}