package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowTempcomputerBackDetail;

public interface FlowTempcomputerBackDetailMapper {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(FlowTempcomputerBackDetail record);

	FlowTempcomputerBackDetail selectByPrimaryKey(Integer id);

	List<FlowTempcomputerBackDetail> selectAll();

	List<FlowTempcomputerBackDetail> selectSelective(FlowTempcomputerBackDetail record);

	int updateByPrimaryKeySelective(FlowTempcomputerBackDetail record);

}