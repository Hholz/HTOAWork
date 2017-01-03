package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StuApplyHourseMapper;
import com.ht.popj.student.StuApplyHourse;
import com.ht.service.student.StuApplyHourseService;

public class StudApplyHourseServiceImpl implements StuApplyHourseService{

	@Autowired StuApplyHourseMapper stuApplyHourseMapper;
	

	@Override
	public List<StuApplyHourse> selectByDynamic(StuApplyHourse record) {
		// TODO Auto-generated method stub
		return stuApplyHourseMapper.selectByDynamic(record);
	}


	@Override
	public List<StuApplyHourse> selectStuApplyHourseAll() {
		// TODO Auto-generated method stub
		return stuApplyHourseMapper.selectStuApplyHourseAll();
	}


	@Override
	public int insert(StuApplyHourse record) {
		// TODO Auto-generated method stub
		return stuApplyHourseMapper.insert(record);
	}


	@Override
	public int insertSelective(StuApplyHourse record) {
		// TODO Auto-generated method stub
		return stuApplyHourseMapper.insertSelective(record);
	}


	@Override
	public int updateByPrimaryKeySelective(StuApplyHourse record) {
		// TODO Auto-generated method stub
		return stuApplyHourseMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return stuApplyHourseMapper.deleteByPrimaryKey(id);
	}

}
