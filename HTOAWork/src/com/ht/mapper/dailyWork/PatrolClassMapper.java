package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.PatrolClass;

public interface PatrolClassMapper {
	//�б�
    List<PatrolClass> findPatrolClassList1(PatrolClass patrolClass);
    List<PatrolClass> findPatrolClassList2();
    //����
    int addPatrolClass(PatrolClass patrolClass);
    //�޸�
    int updatePatrolClass(PatrolClass  patrolClass);
    //ɾ��
    int deletePatrolClassById(Integer id);
}