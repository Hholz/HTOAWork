package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowRole;

public interface FlowRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowRole record);

    int insertSelective(FlowRole record);

    List<FlowRole> selectAll();
    
    List<FlowRole> selectAllByinvalid();
    
    FlowRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowRole record);

    int updateByPrimaryKey(FlowRole record);
}