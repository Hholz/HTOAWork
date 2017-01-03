package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.FinanceSalary;

public interface FinanceSalaryService {
	int deleteByPrimaryKey(Integer id);

    int insert(FinanceSalary record);

    FinanceSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(FinanceSalary record);
    
    List<FinanceSalary> selectAllList(FinanceSalary record);
    
    Integer selectSumSalary(String id);
}
