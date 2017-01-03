package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowTempcomputerApplyDetail;

public interface FlowTempcomputerApplyDetailService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowTempcomputerApplyDetail record);

    FlowTempcomputerApplyDetail selectByPrimaryKey(Integer id);
    
	List<FlowTempcomputerApplyDetail> selectAll();

	List<FlowTempcomputerApplyDetail> selectSelective(FlowTempcomputerApplyDetail record);

    int updateByPrimaryKeySelective(FlowTempcomputerApplyDetail record);
}
