package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowOvertimeDetail;

public interface FlowOvertimeDetailService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowOvertimeDetail record);

    FlowOvertimeDetail selectByPrimaryKey(Integer id);
    
	List<FlowOvertimeDetail> selectAll();

	List<FlowOvertimeDetail> selectSelective(FlowOvertimeDetail record);

    int updateByPrimaryKeySelective(FlowOvertimeDetail record);
}
