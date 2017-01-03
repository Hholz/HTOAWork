package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowComputerRepair;

public interface FlowComputerRepairService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowComputerRepair record);

    FlowComputerRepair selectByPrimaryKey(Integer id);
    
	List<FlowComputerRepair> selectAll();

	List<FlowComputerRepair> selectSelective(FlowComputerRepair record);

    int updateByPrimaryKeySelective(FlowComputerRepair record);
}
