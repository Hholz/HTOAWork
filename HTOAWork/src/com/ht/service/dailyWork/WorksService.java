package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Works;

public interface WorksService {
    int deleteByPrimaryKey(Integer id);

    int insert(Works record);

    Works selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Works record);
    
    List<Works> selectWorks(Works record);
}
