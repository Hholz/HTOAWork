package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.PatrolHouse;

public interface PatrolHouseService {
	//�б�
	List<PatrolHouse> findPatrolHouseList1(PatrolHouse patrolHouse);
	List<PatrolHouse> findPatrolHouseList2();
	//����
	int addPatrolHouse(PatrolHouse patrolHouse);
	//�޸�
	int updatePatrolHouse(PatrolHouse patrolHouse);
	//ɾ��
	int deletePatrolHouseById(Integer id);
	
}