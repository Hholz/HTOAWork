package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowSupplementDetail;
import com.ht.popj.flow.FlowSwaparrangeDetail;

public interface FlowSupplementDetailService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowSupplementDetail record);

    FlowSupplementDetail selectByPrimaryKey(Integer id);
    
    List<FlowSupplementDetail> selectAll();
    
    List<FlowSupplementDetail> selectSelective(FlowSupplementDetail record);
    
	List<FlowSupplementDetail> selectSupplementList(String empid);

    int updateByPrimaryKeySelective(FlowSupplementDetail record);
    
    List<FlowSupplementDetail> selectBySulementId(Integer record);
}
