package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowHuorApplyDetail;

public interface FlowHuorApplyDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowHuorApplyDetail record);

    FlowHuorApplyDetail selectByPrimaryKey(Integer id);
    
    List<FlowHuorApplyDetail> selectAll();

	List<FlowHuorApplyDetail> selectSelective(FlowHuorApplyDetail record);

    int updateByPrimaryKeySelective(FlowHuorApplyDetail record);

}