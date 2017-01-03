package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowFeedBackDetail;

public interface FlowFeedBackDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowFeedBackDetail record);

    FlowFeedBackDetail selectByPrimaryKey(Integer id);
    
    List<FlowFeedBackDetail> selectFeedBackDetail(String empid);
    
    List<FlowFeedBackDetail> selectAll(int id);

	List<FlowFeedBackDetail> selectSelective(FlowFeedBackDetail record);

    int updateByPrimaryKeySelective(FlowFeedBackDetail record);
    
    List<FlowFeedBackDetail> selectByFeedBackId(int id);

}