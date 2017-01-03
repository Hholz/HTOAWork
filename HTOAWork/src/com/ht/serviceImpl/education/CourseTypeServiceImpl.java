package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.education.EduCourseTypeMapper;
import com.ht.popj.education.EduCourseType;
import com.ht.popj.education.EduCourseTypeExample;
import com.ht.service.education.CourseTypeService;

public class CourseTypeServiceImpl implements CourseTypeService{

	@Autowired
	EduCourseTypeMapper typeMapper;

	@Override
	public int countByExample(EduCourseTypeExample example) {
		return typeMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(EduCourseTypeExample example) {
		return typeMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return typeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EduCourseType record) {
		return typeMapper.insert(record);
	}

	@Override
	public int insertSelective(EduCourseType record) {
		return typeMapper.insertSelective(record);
	}

	@Override
	public List<EduCourseType> selectByExample(EduCourseTypeExample example) {
		return typeMapper.selectByExample(example);
	}

	@Override
	public EduCourseType selectByPrimaryKey(Integer id) {
		return typeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(EduCourseType record, EduCourseTypeExample example) {
		return typeMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(EduCourseType record, EduCourseTypeExample example) {
		return typeMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(EduCourseType record) {
		return typeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EduCourseType record) {
		return typeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<EduCourseType> selectCourseTypeAll() {
		return typeMapper.selectCourseTypeAll();
	}

	@Override
	public List<EduCourseType> selectByDynamic(EduCourseType type) {
		return typeMapper.selectByDynamic(type);
	}
	
}
