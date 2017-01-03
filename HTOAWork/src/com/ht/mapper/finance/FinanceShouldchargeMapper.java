package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.FinanceShouldcharge;

public interface FinanceShouldchargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceShouldcharge record);

    FinanceShouldcharge selectByPrimaryKey(FinanceShouldcharge record);
    
    FinanceShouldcharge selectById(Integer id);

    int updateByPrimaryKey(FinanceShouldcharge record);
    
    List<FinanceShouldcharge> selectAllList(FinanceShouldcharge record);
    
    List<FinanceShouldcharge> selectByDynamic(FinanceShouldcharge record);
}