package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowSwaparrangeDetail;

public interface FlowSwaparrangeDetailService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowSwaparrangeDetail record);

    FlowSwaparrangeDetail selectByPrimaryKey(Integer id);
    
	List<FlowSwaparrangeDetail> selectAll();

	List<FlowSwaparrangeDetail> selectSelective(FlowSwaparrangeDetail record);

    int updateByPrimaryKeySelective(FlowSwaparrangeDetail record);
    
    List<FlowSwaparrangeDetail> selectBySulementId(Integer id);
}
