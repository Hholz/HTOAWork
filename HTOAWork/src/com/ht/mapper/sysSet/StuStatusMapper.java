package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.StuStatus;

public interface StuStatusMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(StuStatus record);

    StuStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuStatus record);

    int delByUpdate(Integer id);
    
    List<StuStatus> selectAll();
}