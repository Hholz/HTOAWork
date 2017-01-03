package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowAll;

public interface FlowAllMapper {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(FlowAll record);

	FlowAll selectByPrimaryKey(Integer id);

	FlowAll selectBytypeid(FlowAll record);
    
    List<FlowAll> selectFlowAllStud(FlowAll record);
    
    List<FlowAll> selectFlowAllEmp(FlowAll all);
    
    List<FlowAll> selectFlowAllEmp1(FlowAll all);
    
    List<FlowAll> selectFlowAllStud1(FlowAll record);

	List<FlowAll> selectAll();

	List<FlowAll> selectSelective(FlowAll record);

	int updateByPrimaryKeySelective(FlowAll record);

	int updateFlowAllStatus(FlowAll all);

}