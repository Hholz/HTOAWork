package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.SysHoliday;

public interface SysHolidayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysHoliday record);

    int insertSelective(SysHoliday record);

    SysHoliday selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysHoliday record);

    int updateByPrimaryKey(SysHoliday record);
    
    List<SysHoliday> setholidayselect(SysHoliday record);
}