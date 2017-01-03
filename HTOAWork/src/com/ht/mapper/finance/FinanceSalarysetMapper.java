package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.FinanceSalaryset;

public interface FinanceSalarysetMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FinanceSalaryset record);

    FinanceSalaryset selectByPrimaryKey(Integer id);
    
    List<FinanceSalaryset> selectByName(FinanceSalaryset record);

    int updateByPrimaryKeySelective(FinanceSalaryset record);

}