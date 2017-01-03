package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StuOptionSort;

public interface StuOptionSortMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StuOptionSort record);

    int insertSelective(StuOptionSort record);

    StuOptionSort selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuOptionSort record);

    int updateByPrimaryKey(StuOptionSort record);
    
    List<StuOptionSort> selectDynamic(StuOptionSort record);
}