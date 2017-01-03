package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowStudentApply;

public interface FlowStudentApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowStudentApply record);

    FlowStudentApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowStudentApply record);
    
    List<FlowStudentApply> selectStudentApplyList(FlowStudentApply record);
    
    List<FlowStudentApply> selectAll();
    
    List<FlowStudentApply> selectSelective(FlowStudentApply record);

}