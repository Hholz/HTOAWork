package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowApplyMaterial;

public interface FlowApplyMaterialMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowApplyMaterial record);

    FlowApplyMaterial selectByPrimaryKey(Integer id);
    
	List<FlowApplyMaterial> selectAll();

	List<FlowApplyMaterial> selectSelective(FlowApplyMaterial record);

    int updateByPrimaryKeySelective(FlowApplyMaterial record);

}