package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowHoliday;

public interface FlowHolidayMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowHoliday record);

    FlowHoliday selectByPrimaryKey(Integer id);
    
    List<FlowHoliday> selectAll();
    
    List<FlowHoliday> selectSelective(FlowHoliday record);

    int updateByPrimaryKeySelective(FlowHoliday record);
    
    List<FlowHoliday> selectMyHoliday(FlowHoliday record);
    
    int insertEmpHoliday(FlowHoliday record);
    
    List<FlowHoliday> selectMyEmpHoliday(FlowHoliday record);
    
    List<FlowHoliday> selectMyholidayEmp(int i);

}