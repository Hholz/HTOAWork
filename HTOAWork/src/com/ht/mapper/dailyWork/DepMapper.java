package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Dep;

public interface DepMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dep record);

    Dep selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Dep record);
    
    List<Dep> selectDepList();
    
    List<Dep> selectChildenDep(Integer id);
    
    List<Dep> selectDepNameList();
}