package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentSayfaceMapper;
import com.ht.popj.student.StudentSayface;
import com.ht.service.student.StudentSayhService;


public class StudentSayhServiceImpl implements StudentSayhService{

	@Autowired
	StudentSayfaceMapper studentsayh;
	
	@Override
	public List<StudentSayface> studentsayheart(StudentSayface record) {
		// TODO Auto-generated method stub
		return studentsayh.studentsayheart(record);
	}

	@Override
	public int insertSelective(StudentSayface record) {
		// TODO Auto-generated method stub
		return studentsayh.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(StudentSayface record) {
		// TODO Auto-generated method stub
		studentsayh.updateByPrimaryKeySelective(record);
		return 0;
	}
	
}
