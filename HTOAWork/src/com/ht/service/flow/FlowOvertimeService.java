package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowOvertime;

public interface FlowOvertimeService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowOvertime record);

    FlowOvertime selectByPrimaryKey(Integer id);
    
	List<FlowOvertime> selectAll();

	List<FlowOvertime> selectSelective(FlowOvertime record);

    int updateByPrimaryKeySelective(FlowOvertime record);
}
