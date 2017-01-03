package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowTempcomputerBack;

public interface FlowTempcomputerBackMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowTempcomputerBack record);

    FlowTempcomputerBack selectByPrimaryKey(Integer id);
    
    List<FlowTempcomputerBack> selectAll();

	List<FlowTempcomputerBack> selectSelective(FlowTempcomputerBack record);

    int updateByPrimaryKeySelective(FlowTempcomputerBack record);

}