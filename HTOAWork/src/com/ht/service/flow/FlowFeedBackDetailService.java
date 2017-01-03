package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowFeedBackDetail;

public interface FlowFeedBackDetailService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowFeedBackDetail record);

    FlowFeedBackDetail selectByPrimaryKey(Integer id);
    
    List<FlowFeedBackDetail> selectAll(int id);

	List<FlowFeedBackDetail> selectSelective(FlowFeedBackDetail record);

    int updateByPrimaryKeySelective(FlowFeedBackDetail record);
}
