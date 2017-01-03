package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentLayerMapper;
import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentLayer;
import com.ht.service.student.StudentLayerService;

public class StudentLayerServiceImpl implements StudentLayerService {

	@Autowired StudentLayerMapper studentLayerMapper;

	@Override
	public List<StudentLayer> selectStudentLayerAll() {
		// TODO Auto-generated method stub
		return studentLayerMapper.selectStudentLayerAll();
	}

	@Override
	public int insertSelective(StudentLayer record) {
		// TODO Auto-generated method stub
		return studentLayerMapper.insertSelective(record);
	}

	@Override
	public int insert(StudentLayer record) {
		// TODO Auto-generated method stub
		return studentLayerMapper.insert(record);
	}

	@Override
	public int updateLayerStatusByPrimaryKey(Integer id) {
	
		return studentLayerMapper.updateLayerStatusPrimaryKey(id);
	}

	@Override
	public List<StudentLayer> selectStudentLayerNameAll() {
		// TODO Auto-generated method stub
		return studentLayerMapper.selectStudentLayerNameAll();
	}

	@Override
	public List<StudentLayer> selectDynamic(StudentLayer record) {
		// TODO Auto-generated method stub
		return studentLayerMapper.selectDynamic(record);
	}

	


}
