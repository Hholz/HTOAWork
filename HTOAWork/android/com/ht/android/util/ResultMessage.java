package com.ht.android.util;

import java.io.Serializable;

public class ResultMessage implements Serializable{

	private String resultCode;
	private String resultMessage;
	private Object Context;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public Object getContext() {
		return Context;
	}
	public void setContext(Object context) {
		Context = context;
	}
}
