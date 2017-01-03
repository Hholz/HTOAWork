package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.education.EduCourseTeacherMapper;
import com.ht.popj.education.EduCourseTeacher;
import com.ht.popj.education.EduCourseTeacherExample;
import com.ht.service.education.CourseTeacherService;

public class CourseTeacherServiceImpl implements CourseTeacherService{

	@Autowired
	EduCourseTeacherMapper teacherMapper;
	
	@Override
	public int countByExample(EduCourseTeacherExample example) {
		return teacherMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(EduCourseTeacherExample example) {
		return teacherMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return teacherMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EduCourseTeacher record) {
		return teacherMapper.insert(record);
	}

	@Override
	public int insertSelective(EduCourseTeacher record) {
		return teacherMapper.insertSelective(record);
	}

	@Override
	public List<EduCourseTeacher> selectByExample(EduCourseTeacherExample example) {
		return teacherMapper.selectByExample(example);
	}

	@Override
	public EduCourseTeacher selectByPrimaryKey(Integer id) {
		return teacherMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(EduCourseTeacher record) {
		return teacherMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EduCourseTeacher record) {
		return teacherMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<EduCourseTeacher> selectTeacherAll() {
		return teacherMapper.selectTeacherAll();
	}

	@Override
	public List<EduCourseTeacher> selectByDynamic(EduCourseTeacher course) {
		return teacherMapper.selectByDynamic(course);
	}

	@Override
	public int getCount() {
		return teacherMapper.getCount();
	}

}
