package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StuOptionSortMapper;
import com.ht.popj.student.StuOptionSort;
import com.ht.service.student.StuOptionSortService;

public class StuOptionSortServiceImpl implements StuOptionSortService{

	@Autowired StuOptionSortMapper stuOptionSortMapper;
	@Override
	public List<StuOptionSort> selectDynamic(StuOptionSort record) {
		// TODO Auto-generated method stub
		return stuOptionSortMapper.selectDynamic(record);
	}
	@Override
	public StuOptionSort selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return stuOptionSortMapper.selectByPrimaryKey(id);
	}

}
