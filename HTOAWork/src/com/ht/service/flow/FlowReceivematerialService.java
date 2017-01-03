package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowReceivematerial;

public interface FlowReceivematerialService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowReceivematerial record);

    FlowReceivematerial selectByPrimaryKey(Integer id);
    
    List<FlowReceivematerial> selectAll();
    
    List<FlowReceivematerial> selectSelective(FlowReceivematerial record);

    int updateByPrimaryKeySelective(FlowReceivematerial record);
}
