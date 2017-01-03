package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowHuorApply;

public interface FlowHuorApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowHuorApply record);

    FlowHuorApply selectByPrimaryKey(Integer id);
    
    List<FlowHuorApply> selectAll();

	List<FlowHuorApply> selectSelective(FlowHuorApply record);

    int updateByPrimaryKeySelective(FlowHuorApply record);

}