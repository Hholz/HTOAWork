package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.EducationMapper;
import com.ht.popj.dailyWork.Education;
import com.ht.service.dailyWork.EducationService;

public class EducationServiceImpl implements EducationService{

	@Autowired
	EducationMapper educationMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return educationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Education record) {
		return educationMapper.insert(record);
	}

	@Override
	public Education selectByPrimaryKey(Integer id) {
		return educationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Education record) {
		return educationMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Education> selectEducation(Education record) {
		return educationMapper.selectEducation(record);
	}

}
