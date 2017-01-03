package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowBaoxiao;

public interface FlowBaoxiaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowBaoxiao record);

    FlowBaoxiao selectByPrimaryKey(Integer id);
    
	List<FlowBaoxiao> selectAll();

	List<FlowBaoxiao> selectSelective(FlowBaoxiao record);

    int updateByPrimaryKeySelective(FlowBaoxiao record);

}