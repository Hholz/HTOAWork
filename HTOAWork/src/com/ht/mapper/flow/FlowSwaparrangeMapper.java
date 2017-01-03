package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowSwaparrange;

public interface FlowSwaparrangeMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowSwaparrange record);

    FlowSwaparrange selectByPrimaryKey(Integer id);
    
	List<FlowSwaparrange> selectAll();

	List<FlowSwaparrange> selectSelective(FlowSwaparrange record);

    int updateByPrimaryKeySelective(FlowSwaparrange record);

}