package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowSwaparrangeDetail;

public interface FlowSwaparrangeDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowSwaparrangeDetail record);

    FlowSwaparrangeDetail selectByPrimaryKey(Integer id);
    
	List<FlowSwaparrangeDetail> selectAll();

	List<FlowSwaparrangeDetail> selectSelective(FlowSwaparrangeDetail record);

    int updateByPrimaryKeySelective(FlowSwaparrangeDetail record);
    
    List<FlowSwaparrangeDetail> selectBySulementId(Integer id);

}