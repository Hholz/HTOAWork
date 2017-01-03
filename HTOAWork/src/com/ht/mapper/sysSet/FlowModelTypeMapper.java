package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FlowModelType;

public interface FlowModelTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowModelType record);

    int insertSelective(FlowModelType record);

    FlowModelType selectByPrimaryKey(Integer id);
    
    List<FlowModelType> selectByName(FlowModelType record);
    
    List<FlowModelType> selectList();

    int updateByPrimaryKeySelective(FlowModelType record);

    int updateByPrimaryKey(FlowModelType record);
    
    List<FlowModelType> findInfo(int id);
}