package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowRole;

public interface FlowRoleService {
	int deleteByPrimaryKey(Integer id);

    int insert(FlowRole record);

    int insertSelective(FlowRole record);

    FlowRole selectByPrimaryKey(Integer id);
    
    List<FlowRole> selectAll();

    int updateByPrimaryKeySelective(FlowRole record);

    int updateByPrimaryKey(FlowRole record);
}
