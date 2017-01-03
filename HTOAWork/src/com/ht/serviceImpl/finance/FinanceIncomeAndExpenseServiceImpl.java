package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceIncomeAndExpenseMapper;
import com.ht.popj.finance.FinanceBalance;
import com.ht.service.finance.FinanceIncomeAndExpenseService;

public class FinanceIncomeAndExpenseServiceImpl implements FinanceIncomeAndExpenseService {

	@Autowired
	FinanceIncomeAndExpenseMapper mapper;
	@Override
	public List<FinanceBalance> selectFinanceBalance(FinanceBalance balance) {
		return mapper.selectFinanceBalance(balance);
	}
	@Override
	public List<FinanceBalance> getIncomeAndExpenseSum(FinanceBalance balance) {
		return mapper.getIncomeAndExpenseSum(balance);
	}
	@Override
	public List<FinanceBalance> getTypeSumIn(FinanceBalance balance) {
		return mapper.getTypeSumIn(balance);
	}
	@Override
	public List<FinanceBalance> getTypeSumOut(FinanceBalance balance) {
		return mapper.getTypeSumOut(balance);
	}

}
