package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.ApproveDot;

public interface ApproveDotService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ApproveDot record);

    List<ApproveDot> selectByPrimaryKey(Integer id);
    
    List<ApproveDot> selectByName(ApproveDot id);
    List<ApproveDot> selectlist();

    int updateByPrimaryKeySelective(ApproveDot record);
}
