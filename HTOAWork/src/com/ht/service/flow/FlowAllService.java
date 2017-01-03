package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowAll;

public interface FlowAllService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowAll record);

    FlowAll selectByPrimaryKey(Integer id);
    
    List<FlowAll> selectAll();
    
    List<FlowAll> selectSelective(FlowAll record);

    int updateByPrimaryKeySelective(FlowAll record);
    
    List<FlowAll> selectFlowAllStud(FlowAll record);
    
    List<FlowAll> selectFlowAllEmp(FlowAll all);
    
    List<FlowAll> selectFlowAllEmp1(FlowAll all);
    
    List<FlowAll> selectFlowAllStud1(FlowAll record);
}
