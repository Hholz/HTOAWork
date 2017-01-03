package com.ht.serviceImpl.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceYearReportMapper;
import com.ht.popj.finance.FinanceMonth;
import com.ht.popj.finance.FinanceYear;
import com.ht.service.finance.FinanceYearService;

public class FinanceYearServiceImpl implements FinanceYearService{

	@Autowired
	FinanceYearReportMapper mapper;
	@Override
	public List<FinanceYear> getYearIN(int syear, int eyear) {
		Map map = new HashMap();
		map.put("syear", syear);
		map.put("eyear", eyear);
		return mapper.getYearIN(map);
	}

	@Override
	public List<FinanceYear> getYearOut(int syear, int eyear) {
		Map map = new HashMap();
		map.put("syear", syear);
		map.put("eyear", eyear);
		return mapper.getYearOut(map);
	}

	@Override
	public List<FinanceMonth> getMonth(int syear) {
		Map map = new HashMap();
		map.put("syear", syear);
		return mapper.getMonth(map);
	}

}
