package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowReturnmaterialdetail;

public interface FlowReturnmaterialdetailService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowReturnmaterialdetail record);

    FlowReturnmaterialdetail selectByPrimaryKey(Integer id);
    
    List<FlowReturnmaterialdetail> selectAll();

	List<FlowReturnmaterialdetail> selectSelective(FlowReturnmaterialdetail record);

    int updateByPrimaryKeySelective(FlowReturnmaterialdetail record);
    
    List<FlowReturnmaterialdetail> selectLength(Integer id);
    
    List<FlowReturnmaterialdetail> selectReturnMateriallist(String empid);
}
