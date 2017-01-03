package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.Flowschedule;

public interface FlowscheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Flowschedule record);

    Flowschedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Flowschedule record);
    
    List<Flowschedule> selectByName(Flowschedule record);
}