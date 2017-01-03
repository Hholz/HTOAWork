package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FinanceAttencerewardset;

public interface FinattenRewardService {

	int deleteByPrimaryKey(Integer id);

	int insert(FinanceAttencerewardset record);

	int insertSelective(FinanceAttencerewardset record);

	FinanceAttencerewardset selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(FinanceAttencerewardset record);

	int updateByPrimaryKey(FinanceAttencerewardset record);

	List<FinanceAttencerewardset> finattenrewardsel(FinanceAttencerewardset record);
}
