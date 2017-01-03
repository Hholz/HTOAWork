package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowSupplementDetail;
import com.ht.popj.flow.FlowSwaparrangeDetail;

public interface FlowSupplementDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowSupplementDetail record);

    FlowSupplementDetail selectByPrimaryKey(Integer id);
    
    List<FlowSupplementDetail> selectAll();
    
	List<FlowSupplementDetail> selectSupplementList(String empid);
    
    List<FlowSupplementDetail> selectSelective(FlowSupplementDetail record);

    int updateByPrimaryKeySelective(FlowSupplementDetail record);
    
    List<FlowSupplementDetail> selectBySulementId(Integer record);

}