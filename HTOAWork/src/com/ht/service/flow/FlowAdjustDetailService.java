package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowAdjustDetail;

public interface FlowAdjustDetailService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowAdjustDetail record);

    FlowAdjustDetail selectByPrimaryKey(Integer id);
    
    List<FlowAdjustDetail> selectAll();

	List<FlowAdjustDetail> selectSelective(FlowAdjustDetail record);

    int updateByPrimaryKeySelective(FlowAdjustDetail record);
}
