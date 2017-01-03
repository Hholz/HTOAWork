package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowFeedBack;

public interface FlowFeedBackService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowFeedBack record);

    FlowFeedBack selectByPrimaryKey(Integer id);
    
    List<FlowFeedBack> selectAll();
    
    List<FlowFeedBack> selectSelective(FlowFeedBack record);

    int updateByPrimaryKeySelective(FlowFeedBack record);
}
