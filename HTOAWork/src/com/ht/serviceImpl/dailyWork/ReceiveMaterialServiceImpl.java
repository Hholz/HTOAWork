package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.ReceiveMaterialMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.ReceiveMaterial;
import com.ht.service.dailyWork.ReceiveMaterialService;

public class ReceiveMaterialServiceImpl implements ReceiveMaterialService{
	
	@Autowired
	ReceiveMaterialMapper remm;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		remm.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(ReceiveMaterial record) {
		remm.insertSelective(record);
		return record.getId();
	}

	@Override
	public ReceiveMaterial selectByPrimaryKey(Integer id) {
		
		return remm.selectByPrimaryKey(id);
	}

	@Override
	public List<ReceiveMaterial> selectByName(ReceiveMaterial record) {
		return remm.selectByName(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ReceiveMaterial record) {
		remm.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public Integer selectTask(Emp record) {
		return remm.selectTask(record);
	}

}
