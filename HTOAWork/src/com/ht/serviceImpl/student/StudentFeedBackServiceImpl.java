package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentFeedBackMapper;
import com.ht.popj.student.StudentFeedBack;
import com.ht.service.student.StudentFeedBackService;

public class StudentFeedBackServiceImpl implements StudentFeedBackService{

	@Autowired
	StudentFeedBackMapper studentFeedBackMapper;
	@Override
	public List<StudentFeedBack> selectAll() {
		// TODO Auto-generated method stub
		return studentFeedBackMapper.selectAll();
	}
	@Override
	public List<StudentFeedBack> slectDynamic(StudentFeedBack stu) {
		// TODO Auto-generated method stub
		return studentFeedBackMapper.selectDynamic(stu);
	}
	@Override
	public int insert(StudentFeedBack feed) {
		// TODO Auto-generated method stub
		return studentFeedBackMapper.insert(feed);
	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return studentFeedBackMapper.selectCount();
	}
	@Override
	public int updateByPrimaryKeySelective(StudentFeedBack record) {
		// TODO Auto-generated method stub
		return studentFeedBackMapper.updateByPrimaryKeySelective(record);
	}

}
