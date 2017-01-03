package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowReturnmaterial;

public interface FlowReturnmaterialService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowReturnmaterial record);

    FlowReturnmaterial selectByPrimaryKey(Integer id);
    
	List<FlowReturnmaterial> selectAll();

	List<FlowReturnmaterial> selectSelective(FlowReturnmaterial record);

    int updateByPrimaryKeySelective(FlowReturnmaterial record);
}
