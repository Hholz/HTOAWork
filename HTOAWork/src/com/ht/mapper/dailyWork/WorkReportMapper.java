package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.WorkReport;

public interface WorkReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkReport record);

    int insertSelective(WorkReport record);

    WorkReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkReport record);

    int updateByPrimaryKey(WorkReport record);
    
    List<WorkReport> getAll(WorkReport record);
    
    List<WorkReport> getSome(WorkReport record);
}