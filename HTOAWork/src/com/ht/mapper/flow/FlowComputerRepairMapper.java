package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowComputerRepair;

public interface FlowComputerRepairMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowComputerRepair record);

    FlowComputerRepair selectByPrimaryKey(Integer id);
    
	List<FlowComputerRepair> selectAll();

	List<FlowComputerRepair> selectSelective(FlowComputerRepair record);

    int updateByPrimaryKeySelective(FlowComputerRepair record);

}