package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.WorksMapper;
import com.ht.popj.dailyWork.Works;
import com.ht.service.dailyWork.WorksService;

public class WorksServiceImpl implements WorksService{

	@Autowired
	WorksMapper worksMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return worksMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Works record) {
		return worksMapper.insert(record);
	}

	@Override
	public Works selectByPrimaryKey(Integer id) {
		return worksMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Works record) {
		return worksMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Works> selectWorks(Works record) {
		return worksMapper.selectWorks(record);
	}

}
