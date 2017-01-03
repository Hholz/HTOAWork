package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.FinanceSalaryset;

public interface FinanceSalarySetService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(FinanceSalaryset record);

	FinanceSalaryset selectByPrimaryKey(Integer id);

	List<FinanceSalaryset> selectByName(FinanceSalaryset record);

	int updateByPrimaryKeySelective(FinanceSalaryset record);

}
