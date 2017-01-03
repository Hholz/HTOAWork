package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentEduMapper;
import com.ht.popj.student.StudentEdu;
import com.ht.service.student.StudentEduService;

public class StudentEduServiceImpl implements StudentEduService{

	@Autowired
	StudentEduMapper studentEduMapper;
	@Override
	public int deleteById(Integer id) {
		return studentEduMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StudentEdu studentEdu) {
		return studentEduMapper.insertSelective(studentEdu);
	}

	@Override
	public StudentEdu selectById(Integer id) {
		return studentEduMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StudentEdu studentEdu) {
		return studentEduMapper.updateByPrimaryKeySelective(studentEdu);
	}

	@Override
	public int delByUpdate(Integer id) {
		return studentEduMapper.delByUpdate(id);
	}

	@Override
	public List<StudentEdu> selectAll() {
		return studentEduMapper.selectAll();
	}

	@Override
	public List<StudentEdu> selectBystuId(Integer stuId) {
		return studentEduMapper.selectBystuId(stuId);
	}

}
