package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowModelsel;

public interface FlowModelselService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowModelsel record);

    FlowModelsel selectByPrimaryKey(Integer id);
    
    List<FlowModelsel> selectAll();
    
    List<FlowModelsel> selectSelective(FlowModelsel record);

    int updateByPrimaryKeySelective(FlowModelsel record);
}
