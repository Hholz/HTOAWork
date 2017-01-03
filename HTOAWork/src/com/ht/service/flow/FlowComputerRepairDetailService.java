package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowComputerRepairDetail;

public interface FlowComputerRepairDetailService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowComputerRepairDetail record);

    FlowComputerRepairDetail selectByPrimaryKey(Integer id);
    
    List<FlowComputerRepairDetail> selectAll();
    
    List<FlowComputerRepairDetail> selectSelective(FlowComputerRepairDetail record);

    int updateByPrimaryKeySelective(FlowComputerRepairDetail record);
}
