package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.FinanceFeedbackdetail;

public interface FinanceFeedbackdetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceFeedbackdetail record);

    int insertSelective(FinanceFeedbackdetail record);

    FinanceFeedbackdetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceFeedbackdetail record);

    int updateByPrimaryKey(FinanceFeedbackdetail record);
    
    List<FinanceFeedbackdetail> selectAll();
    List<FinanceFeedbackdetail> selectDynamic(FinanceFeedbackdetail record);
    
    int countScoreByfeedbackId(int id);
}