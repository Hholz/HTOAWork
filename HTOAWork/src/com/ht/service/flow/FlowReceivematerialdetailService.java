package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowReceivematerialdetail;

public interface FlowReceivematerialdetailService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowReceivematerialdetail record);

    FlowReceivematerialdetail selectByPrimaryKey(Integer id);
    
    List<FlowReceivematerialdetail> selectAll();
    
    List<FlowReceivematerialdetail> selectSelective(FlowReceivematerialdetail record);

    int updateByPrimaryKeySelective(FlowReceivematerialdetail record);
    
    List<FlowReceivematerialdetail> selectLength(Integer id);
    
    List<FlowReceivematerialdetail> selectReceiveMateriallist(String empid);
}
