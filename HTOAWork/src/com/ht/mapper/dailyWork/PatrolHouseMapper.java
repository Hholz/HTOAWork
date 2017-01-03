package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.PatrolHouse;

public interface PatrolHouseMapper {
    //列表
	List<PatrolHouse> findPatrolHouseList1(PatrolHouse patrolHouse);
	List<PatrolHouse> findPatrolHouseList2();
	//新增
	int addPatrolHouse(PatrolHouse patrolHouse);
	//修改
	int updatePatrolHouse(PatrolHouse patrolHouse);
	//删除
	int deletePatrolHouseById(Integer id);
}