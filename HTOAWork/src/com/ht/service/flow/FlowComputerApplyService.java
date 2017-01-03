package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowComputerApply;

public interface FlowComputerApplyService {

	int insertSelective(FlowComputerApply record);

    List<FlowComputerApply> selectSelective(FlowComputerApply record);

    int updateByPrimaryKeySelective(FlowComputerApply record);
    
    List<FlowComputerApply> selectAllComputerApply(FlowComputerApply computerApply);
}
