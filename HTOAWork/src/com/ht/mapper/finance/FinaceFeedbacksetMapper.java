package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.FinaceFeedbackset;

public interface FinaceFeedbacksetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinaceFeedbackset record);

    int insertSelective(FinaceFeedbackset record);

    FinaceFeedbackset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinaceFeedbackset record);

    int updateByPrimaryKey(FinaceFeedbackset record);
    
    List<FinaceFeedbackset> selectFinanceFeedbacksetAll();
    //¶¯Ì¬²éÑ¯
    List<FinaceFeedbackset> selectFinanceFeedbacksetByDynamic(FinaceFeedbackset finance);
    
}