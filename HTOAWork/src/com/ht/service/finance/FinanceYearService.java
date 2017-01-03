package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.FinanceMonth;
import com.ht.popj.finance.FinanceYear;

public interface FinanceYearService {

	List<FinanceYear> getYearIN(int syear, int eyear);

	List<FinanceYear> getYearOut(int syear, int eyear);

	List<FinanceMonth> getMonth(int syear);
}
