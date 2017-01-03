package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentFamilyMapper;
import com.ht.popj.student.StudentFamily;
import com.ht.service.student.StudentFamilyService;

public class StudentFamilyServiceImpl implements StudentFamilyService{

	@Autowired
	StudentFamilyMapper stuFamMapper;
	@Override
	public int deleteById(Integer id) {
		return stuFamMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StudentFamily studentFam) {
		return stuFamMapper.insertSelective(studentFam);
	}

	@Override
	public StudentFamily selectById(Integer id) {
		return stuFamMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StudentFamily studentFam) {
		return stuFamMapper.updateByPrimaryKeySelective(studentFam);
	}

	@Override
	public int delByUpdate(Integer id) {
		return stuFamMapper.delByUpdate(id);
	}

	@Override
	public List<StudentFamily> selectAll() {
		return stuFamMapper.selectAll();
	}

	@Override
	public List<StudentFamily> selectBystuId(Integer stuId) {
		return stuFamMapper.selectBystuId(stuId);
	}

}
