package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.FinanceSalarylist;

public interface FinanceSalarylistMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FinanceSalarylist record);

    FinanceSalarylist selectByPrimaryKey(Integer id);
    
    List<FinanceSalarylist> selectAll();
    
    List<FinanceSalarylist> selectSelective(FinanceSalarylist record);

    int updateByPrimaryKeySelective(FinanceSalarylist record);

}