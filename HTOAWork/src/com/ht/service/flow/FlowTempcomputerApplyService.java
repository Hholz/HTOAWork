package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowTempcomputerApply;

public interface FlowTempcomputerApplyService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowTempcomputerApply record);

    FlowTempcomputerApply selectByPrimaryKey(Integer id);
    
    List<FlowTempcomputerApply> selectAll();

	List<FlowTempcomputerApply> selectSelective(FlowTempcomputerApply record);

    int updateByPrimaryKeySelective(FlowTempcomputerApply record);
}
