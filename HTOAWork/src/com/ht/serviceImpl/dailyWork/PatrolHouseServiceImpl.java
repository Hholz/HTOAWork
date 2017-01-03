package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.PatrolHouseMapper;
import com.ht.popj.dailyWork.PatrolHouse;
import com.ht.service.dailyWork.PatrolHouseService;

public class PatrolHouseServiceImpl implements PatrolHouseService{
	
	@Autowired
	private PatrolHouseMapper patrolHouseMapper;

	@Override
	public List<PatrolHouse> findPatrolHouseList1(PatrolHouse patrolHouse) {
		return patrolHouseMapper.findPatrolHouseList1(patrolHouse);
	}

	@Override
	public List<PatrolHouse> findPatrolHouseList2() {
		return patrolHouseMapper.findPatrolHouseList2();
	}

	@Override
	public int addPatrolHouse(PatrolHouse patrolHouse) {
		return patrolHouseMapper.addPatrolHouse(patrolHouse);
	}

	@Override
	public int updatePatrolHouse(PatrolHouse patrolHouse) {
		return patrolHouseMapper.updatePatrolHouse(patrolHouse);
	}

	@Override
	public int deletePatrolHouseById(Integer id) {
		return patrolHouseMapper.deletePatrolHouseById(id);
	}

}
