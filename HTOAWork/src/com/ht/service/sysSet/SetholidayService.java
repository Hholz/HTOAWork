package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.SysHoliday;

public interface SetholidayService {

	 int insertSelective(SysHoliday record);

     SysHoliday selectByPrimaryKey(Integer id);

     int updateByPrimaryKeySelective(SysHoliday record);
     
     List<SysHoliday> setholidayselect(SysHoliday record);
	
}
