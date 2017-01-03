package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.Tuitionset;

public interface TuitionsetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tuitionset record);

    int insertSelective(Tuitionset record);

    Tuitionset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tuitionset record);

    int updateByPrimaryKey(Tuitionset record);
    
    List<Tuitionset> selectByDynamic(Tuitionset record);
    
    Integer selCountByTermId(Integer termId);
    
    List<Tuitionset> selectByDynamicStatus(Tuitionset record);
    
    Tuitionset selectByOnlyTuition(Tuitionset record);
}