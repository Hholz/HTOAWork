package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowsModel;

public interface FlowsModelService {
    int deleteByPrimaryKey(Integer id);
    
    void deleteByPrimary(Integer id);

    int insertSelective(FlowsModel record);

    FlowsModel selectByPrimaryKey(Integer id);
    
	List<FlowsModel> selectAll(int id); 
	
	List<FlowsModel> selectAll2(int id); 

	List<FlowsModel> selectSelective(FlowsModel record);

    int updateByPrimaryKeySelective(FlowsModel record);
}
