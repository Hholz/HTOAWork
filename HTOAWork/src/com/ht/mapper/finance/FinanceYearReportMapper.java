package com.ht.mapper.finance;

import java.util.List;
import java.util.Map;

import com.ht.popj.finance.FinanceMonth;
import com.ht.popj.finance.FinanceYear;

public interface FinanceYearReportMapper {

	List<FinanceYear> getYearIN(Map map);

	List<FinanceYear> getYearOut(Map map);

	List<FinanceMonth> getMonth(Map map);
}
