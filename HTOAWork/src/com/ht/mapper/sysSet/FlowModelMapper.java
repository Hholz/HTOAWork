package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FlowModel;

public interface FlowModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowModel record);

    int insertSelective(FlowModel record);

    FlowModel selectByPrimaryKey(Integer id);
    
    List<FlowModel> selectByName(FlowModel record);
    
    List<FlowModel> selectList();

    int updateByPrimaryKeySelective(FlowModel record);

    int updateByPrimaryKey(FlowModel record);
}