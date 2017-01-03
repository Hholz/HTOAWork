package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ht.mapper.student.StudentFloorMapper;
import com.ht.popj.student.StudentFloor;
import com.ht.service.student.StudentFloorService;

@Repository
public class StudentFloorServiceImpl implements StudentFloorService {
	@Autowired
	StudentFloorMapper studentFloorMapper;
	@Override
	public int insert(StudentFloor record) {
		// TODO Auto-generated method stub
		return studentFloorMapper.insert(record);
	}
	@Override
	public List<StudentFloor> selectStudentFloorAll() {
		
		return studentFloorMapper.selectStudentFloorAll();
	}
	@Override
	public List<StudentFloor> selectByDynamic(StudentFloor studentFloor) {
		
		return studentFloorMapper.selectByDynamic(studentFloor);
	}
	@Override
	public int updateByPrimaryKeySelective(StudentFloor record) {
		
		return studentFloorMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int updateStatusPrimaryKey(Integer record) {
		
		return studentFloorMapper.updateStatusPrimaryKey(record);
	}
	@Override
	public int insertSelective(StudentFloor record) {
		// TODO Auto-generated method stub
		return studentFloorMapper.insertSelective(record);
	}

}
