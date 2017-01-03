package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowAdjustDetail;

public interface FlowAdjustDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowAdjustDetail record);

    FlowAdjustDetail selectByPrimaryKey(Integer id);
    
    List<FlowAdjustDetail> selectAll();

	List<FlowAdjustDetail> selectSelective(FlowAdjustDetail record);

    int updateByPrimaryKeySelective(FlowAdjustDetail record);

}