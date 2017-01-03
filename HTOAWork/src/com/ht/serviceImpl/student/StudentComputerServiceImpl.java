package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentComputerApplyMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.StudentComputerApply;
import com.ht.service.student.StudentComputerService;

public class StudentComputerServiceImpl implements StudentComputerService {

	@Autowired
	StudentComputerApplyMapper studentComputerApplyMapper;
	
	@Override
	public List<StudentComputerApply> studentcomputermanage(
			StudentComputerApply record) {
		// TODO Auto-generated method stub
		return studentComputerApplyMapper.studentcomputermanage(record);
	}

	@Override
	public int updateByPrimaryKeySelective(StudentComputerApply record) {
		// TODO Auto-generated method stub
		return studentComputerApplyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(StudentComputerApply record) {
		// TODO Auto-generated method stub
		return studentComputerApplyMapper.insertSelective(record);
	}

	@Override
	public StudentComputerApply selectById(Integer id) {
		// TODO Auto-generated method stub
		return studentComputerApplyMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer selectNewTask(Emp e) {
		
		return studentComputerApplyMapper.selectNewTask(e);
	}

	@Override
	public Integer selectRepairTask(Emp e) {
		// TODO Auto-generated method stub
		return studentComputerApplyMapper.selectRepairTask(e);
	}

	@Override
	public Integer selectReviceTask(Emp e) {
		// TODO Auto-generated method stub
		return studentComputerApplyMapper.selectReviceTask(e);
	}

	@Override
	public Integer selectReturnTask(Emp e) {
		// TODO Auto-generated method stub
		return studentComputerApplyMapper.selectReturnTask(e);
	}

}
