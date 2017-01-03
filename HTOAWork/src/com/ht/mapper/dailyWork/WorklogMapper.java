package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Worklog;

public interface WorklogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Worklog record);

    Worklog selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Worklog record);
    
    List<Worklog> selectWorklogList(Worklog record);
    
    String selectnexthour(Worklog record);
}