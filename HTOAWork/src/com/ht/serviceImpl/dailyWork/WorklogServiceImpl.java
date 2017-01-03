package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.WorklogMapper;
import com.ht.popj.dailyWork.Worklog;
import com.ht.service.dailyWork.WorklogService;

public class WorklogServiceImpl implements WorklogService{

	@Autowired
	WorklogMapper worklogmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return worklogmapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Worklog record) {
		return worklogmapper.insert(record);
	}

	@Override
	public Worklog selectByPrimaryKey(Integer id) {
		return worklogmapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Worklog record) {
		return worklogmapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Worklog> selectWorklogList(Worklog record) {
		return worklogmapper.selectWorklogList(record);
	}

	@Override
	public String selectnexthour(Worklog record) {
		return worklogmapper.selectnexthour(record);
	}

}
