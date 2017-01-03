package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.ApproveDot;

public interface ApproveDotMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ApproveDot record);

    List<ApproveDot> selectByPrimaryKey(Integer id);
    
    List<ApproveDot> selectByName(ApproveDot id);
    List<ApproveDot> selectlist();

    int updateByPrimaryKeySelective(ApproveDot record);
}