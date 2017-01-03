package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.FinanceBalance;

public interface FinanceIncomeAndExpenseMapper {

	List<FinanceBalance> selectFinanceBalance(FinanceBalance balance);

	List<FinanceBalance> getIncomeAndExpenseSum(FinanceBalance balance);

	List<FinanceBalance> getTypeSumIn(FinanceBalance balance);

	List<FinanceBalance> getTypeSumOut(FinanceBalance balance);
}
