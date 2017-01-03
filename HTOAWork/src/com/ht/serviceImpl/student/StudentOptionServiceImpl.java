package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentOptionMapper;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentOption;
import com.ht.service.student.StudentOptionService;

public class StudentOptionServiceImpl implements StudentOptionService{

	@Autowired StudentOptionMapper studentOptionMapper;
	@Override
	public int insertSelective(StudentOption record) {
		// TODO Auto-generated method stub
		return studentOptionMapper.insertSelective(record);
	}
	@Override
	public List<StudentOption> selectDynamic(StudentOption record) {
		// TODO Auto-generated method stub
		return studentOptionMapper.selectDynamic(record);
	}
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return studentOptionMapper.deleteByPrimaryKey(id);
	}



}
