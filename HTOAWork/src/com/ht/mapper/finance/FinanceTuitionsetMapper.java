package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;
import com.ht.popj.finance.FinanceTuitionset;

public interface FinanceTuitionsetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceTuitionset record);

    int insertSelective(FinanceTuitionset record);

    FinanceTuitionset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceTuitionset record);

    int updateByPrimaryKey(FinanceTuitionset record);
    List<FinanceTuitionset> selectAll();
    List<FinanceTuitionset> selectDinamic(FinanceTuitionset record);
    List<EduMajor> selectAllMajor();
    List<EduTerm> selectAllterm();
}