package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentRoomMapper;
import com.ht.popj.student.StudentRoom;
import com.ht.service.student.StudentRoomService;

public class StudentRoomServiceImpl implements StudentRoomService{

	@Autowired StudentRoomMapper studentRoomMapper;
	@Override
	public List<StudentRoom> selectDynamic(StudentRoom record) {
		// TODO Auto-generated method stub
		return studentRoomMapper.selectDynamic(record);
	}
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return studentRoomMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int insert(StudentRoom record) {
		// TODO Auto-generated method stub
		return studentRoomMapper.insert(record);
	}
	@Override
	public int insertSelective(StudentRoom record) {
		// TODO Auto-generated method stub
		return studentRoomMapper.insertSelective(record);
	}
	@Override
	public StudentRoom selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return studentRoomMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(StudentRoom record) {
		// TODO Auto-generated method stub
		return studentRoomMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int updateByPrimaryKey(StudentRoom record) {
		// TODO Auto-generated method stub
		return studentRoomMapper.updateByPrimaryKey(record);
	}

}
