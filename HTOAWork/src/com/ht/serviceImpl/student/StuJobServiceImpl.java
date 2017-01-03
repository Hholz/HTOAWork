package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentJobsMapper;
import com.ht.popj.student.StudentJobs;
import com.ht.service.student.StuJobService;

public class StuJobServiceImpl implements StuJobService{

	@Autowired
	StudentJobsMapper stuJobMapper;
	@Override
	public int deleteById(Integer id) {
		return stuJobMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StudentJobs studentJob) {
		return stuJobMapper.insertSelective(studentJob);
	}

	@Override
	public StudentJobs selectById(Integer id) {
		return stuJobMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StudentJobs studentJob) {
		return stuJobMapper.updateByPrimaryKeySelective(studentJob);
	}

	@Override
	public int delByUpdate(Integer id) {
		return stuJobMapper.delByUpdate(id);
	}

	@Override
	public List<StudentJobs> selectAll() {
		return stuJobMapper.selectAll();
	}

	@Override
	public StudentJobs selectByStuId(Integer id) {
		return stuJobMapper.selectByStuId(id);
	}

}
