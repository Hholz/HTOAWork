package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.WorkReportMapper;
import com.ht.popj.dailyWork.WorkReport;
import com.ht.service.dailyWork.WorkReportService;

public class WorkReportServiceImpl implements WorkReportService{

	@Autowired
	WorkReportMapper mapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WorkReport record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(WorkReport record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public WorkReport selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WorkReport record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(WorkReport record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public List<WorkReport> getAll(WorkReport record) {
		// TODO Auto-generated method stub
		return mapper.getAll(record);
	}

	@Override
	public List<WorkReport> getSome(WorkReport record) {
		// TODO Auto-generated method stub
		return mapper.getSome(record);
	}

}
