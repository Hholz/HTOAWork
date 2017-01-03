package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.PatrolClass;

public interface PatrolClassService {
	//列表
    List<PatrolClass> findPatrolClassList1(PatrolClass patrolClass);
    List<PatrolClass> findPatrolClassList2();
    //新增
    int addPatrolClass(PatrolClass patrolClass);
    //修改
    int updatePatrolClass(PatrolClass  patrolClass);
    //删除
    int deletePatrolClassById(Integer id);
}
