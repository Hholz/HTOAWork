package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.education.EduCourseMapper;
import com.ht.popj.education.EduCourse;
import com.ht.popj.education.EduCourseExample;
import com.ht.popj.education.EduDept;
import com.ht.service.education.CourseService;

public class CourseServiceImpl implements CourseService{

	@Autowired
	EduCourseMapper courseMapper;
	
	@Override
	public int countByExample(EduCourseExample example) {
		return courseMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(EduCourseExample example) {
		return courseMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return courseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EduCourse record) {
		return courseMapper.insert(record);
	}

	@Override
	public int insertSelective(EduCourse record) {
		return courseMapper.insertSelective(record);
	}

	@Override
	public List<EduCourse> selectByExample(EduCourseExample example) {
		return courseMapper.selectByExample(example);
	}

	@Override
	public EduCourse selectByPrimaryKey(Integer id) {
		return courseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(EduCourse record, EduCourseExample example) {
		return courseMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(EduCourse record, EduCourseExample example) {
		return courseMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(EduCourse record) {
		return courseMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EduCourse record) {
		return courseMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<EduCourse> selectCourseAll() {
		return courseMapper.selectCourseAll();
	}

	@Override
	public List<EduCourse> selectByDynamic(EduCourse course) {
		return courseMapper.selectByDynamic(course);
	}

	@Override
	public int getCount(Integer id) {
		return courseMapper.getCount(id);
	}
	

}
