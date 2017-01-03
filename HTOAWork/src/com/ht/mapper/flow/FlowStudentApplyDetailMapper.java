package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowStudentApplyDetail;

public interface FlowStudentApplyDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowStudentApplyDetail record);

    List<FlowStudentApplyDetail> selectByPrimaryKey(Integer id);
    
    List<FlowStudentApplyDetail> selectAll(int id);
    
    List<FlowStudentApplyDetail> selectStudentApply(String empid);
    
    List<FlowStudentApplyDetail> selectSelective(FlowStudentApplyDetail record);

    int updateByPrimaryKeySelective(FlowStudentApplyDetail record);

}