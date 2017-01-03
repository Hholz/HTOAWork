package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.FinanceFeedbackdetail;

public interface FinanceFeedbackdetailService {
	int insertSelective(FinanceFeedbackdetail record);
	int insert(FinanceFeedbackdetail record);
	List<FinanceFeedbackdetail> selectAll();
	List<FinanceFeedbackdetail> selectDynamic(FinanceFeedbackdetail record);
	int updateByPrimaryKeySelective(FinanceFeedbackdetail record);
	int deleteByPrimaryKey(Integer id);
}
