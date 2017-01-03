package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.FinanceType;

public interface FinanceTypeService {

	int deleteByPrimaryKey(Integer id);

	int insert(FinanceType record);

	int insertSelective(FinanceType record);

	FinanceType selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(FinanceType record);

	int updateByPrimaryKey(FinanceType record);

	List<FinanceType> selectPayTypeAll();

	List<FinanceType> selectByDynamic(FinanceType type);

	int getCount();
}
