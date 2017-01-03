package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.FinanceFeestandard;

public interface FinanceFeestandardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceFeestandard record);

    int insertSelective(FinanceFeestandard record);

    FinanceFeestandard selectByPrimaryKey(Integer id);
    
    FinanceFeestandard selectByPrimaryTypeId(Integer typeId);
    
    int updateByPrimaryKeySelective(FinanceFeestandard record);

    int updateByPrimaryKey(FinanceFeestandard record);
    
	List<FinanceFeestandard> selectFeeStandardAll();

  	List<FinanceFeestandard> selectByDynamic(FinanceFeestandard fee);

  	int getCount();
  	
  	int selectByTypeId(String feeName);
}