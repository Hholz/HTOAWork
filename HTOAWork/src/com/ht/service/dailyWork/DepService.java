package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Dep;

public interface DepService {
	int deleteByPrimaryKey(Integer id);

    int insert(Dep record);

    Dep selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Dep record);
    
    List<Dep> selectDepList();
    
    List<Dep> selectChildenDep(Integer id);
}
