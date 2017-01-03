package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.PatrolClassMapper;
import com.ht.popj.dailyWork.PatrolClass;
import com.ht.service.dailyWork.PatrolClassService;

public class PatrolClassServiceImpl implements PatrolClassService{
	@Autowired
	private PatrolClassMapper patrolClassMapper;

	@Override
	public List<PatrolClass> findPatrolClassList1(PatrolClass patrolClass) {
		return patrolClassMapper.findPatrolClassList1(patrolClass);
	}

	@Override
	public List<PatrolClass> findPatrolClassList2() {
		return patrolClassMapper.findPatrolClassList2();
	}

	@Override
	public int addPatrolClass(PatrolClass patrolClass) {
		return patrolClassMapper.addPatrolClass(patrolClass);
	}

	@Override
	public int updatePatrolClass(PatrolClass patrolClass) {
		return patrolClassMapper.updatePatrolClass(patrolClass);
	}

	@Override
	public int deletePatrolClassById(Integer id) {
		return patrolClassMapper.deletePatrolClassById(id);
	}

}
