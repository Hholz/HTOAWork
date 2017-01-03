package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowsModeltype;

public interface FlowModeltypeService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowsModeltype record);

    FlowsModeltype selectByPrimaryKey(Integer id);
    
    List<FlowsModeltype> selectAll(int id);
    
    List<FlowsModeltype> selectSelective(FlowsModeltype record);

    int updateByPrimaryKeySelective(FlowsModeltype record);
    
    List<FlowsModeltype> selectModelTypeByModelSelId(String modelSelName);
}
