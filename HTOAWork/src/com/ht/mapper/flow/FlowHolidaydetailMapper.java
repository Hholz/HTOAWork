package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowHolidaydetail;

public interface FlowHolidaydetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowHolidaydetail record);

    List<FlowHolidaydetail> selectByPrimaryKey(String empid);
    
	List<FlowHolidaydetail> selectAll(int id);

	List<FlowHolidaydetail> selectSelective(FlowHolidaydetail record);

    int updateByPrimaryKeySelective(FlowHolidaydetail record);
    
    List<FlowHolidaydetail> selectByHolidayid(int holidayid);
    
    List<FlowHolidaydetail> selectEmpHolidayList(String empid);
}