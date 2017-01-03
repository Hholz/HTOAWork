package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowApplyMaterialdetail;

public interface FlowApplyMaterialdetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowApplyMaterialdetail record);

    FlowApplyMaterialdetail selectByPrimaryKey(Integer id);
    
	List<FlowApplyMaterialdetail> selectAll();

	List<FlowApplyMaterialdetail> selectSelective(FlowApplyMaterialdetail record);

    int updateByPrimaryKeySelective(FlowApplyMaterialdetail record);
    
    List<FlowApplyMaterialdetail> selectTaskList(String record);
    
    List<FlowApplyMaterialdetail> selectlength(Integer record);

}