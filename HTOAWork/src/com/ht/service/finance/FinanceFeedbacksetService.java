package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.FinaceFeedbackset;

public interface FinanceFeedbacksetService {
	 int deleteByPrimaryKey(Integer id);
	 int updateByPrimaryKeySelective(FinaceFeedbackset record);
	 int insert(FinaceFeedbackset finance);
	 List<FinaceFeedbackset> selectFinanceFeedbacksetAll();
	    //¶¯Ì¬²éÑ¯
	 List<FinaceFeedbackset> selectFinanceFeedbacksetByDynamic(FinaceFeedbackset finance);
}
