package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentAttencecondMapper;
import com.ht.popj.student.StudentAttencecond;
import com.ht.service.student.StudentAttencondService;

public class StudentAttencondServiceImpl implements StudentAttencondService{

	@Autowired
	StudentAttencecondMapper studentAttencecondMapper;
	
	@Override
	public List<StudentAttencecond> selectByPrimaryKeyall() {
		// TODO Auto-generated method stub
		return studentAttencecondMapper.selectByPrimaryKeyall();
	}

	@Override
	public int insertSelective(StudentAttencecond record) {
		// TODO Auto-generated method stub
		return studentAttencecondMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(StudentAttencecond record) {
		// TODO Auto-generated method stub
		return studentAttencecondMapper.updateByPrimaryKeySelective(record);
	}

}
