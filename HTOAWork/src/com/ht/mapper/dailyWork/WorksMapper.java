package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Works;

public interface WorksMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Works record);

    Works selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Works record);
    
    List<Works> selectWorks(Works record);
}