package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowStudentApply;

public interface FlowStudentApplyService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowStudentApply record);

    FlowStudentApply selectByPrimaryKey(Integer id);
    
	List<FlowStudentApply> selectAll();
	
	List<FlowStudentApply> selectStudentApplyList(FlowStudentApply record);

	List<FlowStudentApply> selectSelective(FlowStudentApply record);
    
    int updateByPrimaryKeySelective(FlowStudentApply record);
}
