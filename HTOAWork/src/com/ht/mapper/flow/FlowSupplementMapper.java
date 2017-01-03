package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowSupplement;

public interface FlowSupplementMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowSupplement record);

    FlowSupplement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowSupplement record);
    
    List<FlowSupplement> selectAll();
    
    List<FlowSupplement> selectSelective(FlowSupplement record);
    
    List<FlowSupplement> selectMySupplement(FlowSupplement record);

}