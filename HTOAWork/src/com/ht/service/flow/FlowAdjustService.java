package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowAdjust;

public interface FlowAdjustService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(FlowAdjust record);

	FlowAdjust selectByPrimaryKey(Integer id);

	List<FlowAdjust> selectAll();

	List<FlowAdjust> selectSelective(FlowAdjust record);

	int updateByPrimaryKeySelective(FlowAdjust record);


}
