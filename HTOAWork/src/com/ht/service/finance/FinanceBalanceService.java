package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.FinanceBalance;

public interface FinanceBalanceService {

    int deleteByPrimaryKey(Integer id);

    int insert(FinanceBalance record);

    int insertSelective(FinanceBalance record);

    FinanceBalance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceBalance record);

    int updateByPrimaryKey(FinanceBalance record);
    
	List<FinanceBalance> selectFinanceBalanceAll();

	List<FinanceBalance> selectByDynamic(FinanceBalance balance);

	List<FinanceBalance> selectByDynamicOfStudent(FinanceBalance balance);
	
	List<FinanceBalance> statisticsAllFee(FinanceBalance balance);
	
	List<FinanceBalance> selectByDynamicOfBalance(FinanceBalance balance);
	
	int getCount();
}
