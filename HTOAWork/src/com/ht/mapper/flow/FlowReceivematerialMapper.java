package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowReceivematerial;

public interface FlowReceivematerialMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowReceivematerial record);

    FlowReceivematerial selectByPrimaryKey(Integer id);
    
    List<FlowReceivematerial> selectAll();
    
    List<FlowReceivematerial> selectSelective(FlowReceivematerial record);

    int updateByPrimaryKeySelective(FlowReceivematerial record);

}