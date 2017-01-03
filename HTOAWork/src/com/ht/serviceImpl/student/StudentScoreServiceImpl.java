package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentScoreMapper;
import com.ht.popj.student.StudentScore;
import com.ht.service.student.StudentScoreService;

public class StudentScoreServiceImpl implements StudentScoreService{

	@Autowired
	StudentScoreMapper studentScoreMapper;
	@Override
	public List<StudentScore> selectAll() {
		// TODO Auto-generated method stub
		return studentScoreMapper.selectAll();
	}

	@Override
	public List<StudentScore> selectDinamic(StudentScore score) {
		// TODO Auto-generated method stub
		return studentScoreMapper.selectDinamic(score);
	}

	@Override
	public int insert(StudentScore record) {
		// TODO Auto-generated method stub
		return studentScoreMapper.insert(record);
	}

	@Override
	public int insertSelective(StudentScore record) {
		// TODO Auto-generated method stub
		return studentScoreMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return studentScoreMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(StudentScore record) {
		// TODO Auto-generated method stub
		return studentScoreMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(StudentScore record) {
		// TODO Auto-generated method stub
		return studentScoreMapper.updateByPrimaryKeySelective(record);
	}

}
