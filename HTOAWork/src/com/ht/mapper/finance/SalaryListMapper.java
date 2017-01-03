package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.SalaryList;

public interface SalaryListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SalaryList record);

    int insertSelective(SalaryList record);

    SalaryList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SalaryList record);

    int updateByPrimaryKey(SalaryList record);
    
    List<SalaryList> selectByDynamic(SalaryList record);
    
    int insertMuch(List<SalaryList> salArr);
}