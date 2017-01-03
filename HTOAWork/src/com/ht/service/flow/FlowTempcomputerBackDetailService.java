package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowTempcomputerBackDetail;

public interface FlowTempcomputerBackDetailService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(FlowTempcomputerBackDetail record);

	FlowTempcomputerBackDetail selectByPrimaryKey(Integer id);

	List<FlowTempcomputerBackDetail> selectAll();

	List<FlowTempcomputerBackDetail> selectSelective(FlowTempcomputerBackDetail record);

	int updateByPrimaryKeySelective(FlowTempcomputerBackDetail record);
}
