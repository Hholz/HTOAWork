package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FinanceSalarytypese;

public interface FinanceSalarytypeseMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(FinanceSalarytypese record);

    FinanceSalarytypese selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceSalarytypese record);
    
    int delByUpdate(Integer id);
    
    List<FinanceSalarytypese> selectAll();
}