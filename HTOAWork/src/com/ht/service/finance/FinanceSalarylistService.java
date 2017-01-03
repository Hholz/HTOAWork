package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.FinanceSalarylist;

public interface FinanceSalarylistService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FinanceSalarylist record);

    FinanceSalarylist selectByPrimaryKey(Integer id);
    
    List<FinanceSalarylist> selectAll();
    
    List<FinanceSalarylist> selectSelective(FinanceSalarylist record);

    int updateByPrimaryKeySelective(FinanceSalarylist record);
}
