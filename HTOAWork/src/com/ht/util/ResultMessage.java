package com.ht.util;

import java.io.Serializable;

public class ResultMessage implements Serializable{

	private int total;
	private Object rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int i) {
		this.total = i;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
	
}
