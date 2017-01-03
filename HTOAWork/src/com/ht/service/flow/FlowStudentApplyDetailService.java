package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowStudentApplyDetail;

public interface FlowStudentApplyDetailService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FlowStudentApplyDetail record);

    List<FlowStudentApplyDetail> selectByPrimaryKey(Integer id);
    
    List<FlowStudentApplyDetail> selectAll(int id);
    
    List<FlowStudentApplyDetail> selectSelective(FlowStudentApplyDetail record);

    int updateByPrimaryKeySelective(FlowStudentApplyDetail record);
}
