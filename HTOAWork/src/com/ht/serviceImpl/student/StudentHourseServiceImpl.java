package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentHourseMapper;
import com.ht.popj.student.StudentHourse;
import com.ht.service.student.StudentHourseService;

public class StudentHourseServiceImpl implements StudentHourseService{

	@Autowired
	StudentHourseMapper studentHourseMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return studentHourseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(StudentHourse record) {
		// TODO Auto-generated method stub
		return studentHourseMapper.insert(record);
	}

	@Override
	public int insertSelective(StudentHourse record) {
		// TODO Auto-generated method stub
		return studentHourseMapper.insertSelective(record);
	}

	@Override
	public StudentHourse selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return studentHourseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(StudentHourse record) {
		// TODO Auto-generated method stub
		return studentHourseMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(StudentHourse record) {
		// TODO Auto-generated method stub
		return studentHourseMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<StudentHourse> selectDynamic(StudentHourse record) {
		// TODO Auto-generated method stub
		return studentHourseMapper.selectDynamic(record);
	}

	@Override
	public List<StudentHourse> findHouseList(StudentHourse house) {
		return studentHourseMapper.findHouseList(house);
	}

}
