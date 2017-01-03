package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.DepMapper;
import com.ht.popj.dailyWork.Dep;
import com.ht.service.dailyWork.DepService;

public class DepServiceImpl  implements DepService{

	@Autowired
	DepMapper depMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return depMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Dep record) {
		return depMapper.insert(record);
	}

	@Override
	public Dep selectByPrimaryKey(Integer id) {
		return depMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Dep record) {
		return depMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Dep> selectDepList() {
		return depMapper.selectDepList();
	}

	@Override
	public List<Dep> selectChildenDep(Integer id) {
		return depMapper.selectChildenDep(id);
	}

}
