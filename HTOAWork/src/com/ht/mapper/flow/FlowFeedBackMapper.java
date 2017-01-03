package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowFeedBack;

public interface FlowFeedBackMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowFeedBack record);

    FlowFeedBack selectByPrimaryKey(Integer id);
    
    List<FlowFeedBack> selectAll();
    
    List<FlowFeedBack> selectMyFeedBack(FlowFeedBack feedBack);
    
    List<FlowFeedBack> selectSelective(FlowFeedBack record);

    int updateByPrimaryKeySelective(FlowFeedBack record);

}