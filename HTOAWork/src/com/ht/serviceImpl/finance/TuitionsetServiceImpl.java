package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.TuitionsetMapper;
import com.ht.popj.finance.Tuitionset;
import com.ht.service.finance.TuitionsetService;

public class TuitionsetServiceImpl implements TuitionsetService{

	@Autowired
	TuitionsetMapper tuitionsetMapper;
	@Override
	public List<Tuitionset> selectByDynamic(Tuitionset record) {
		// TODO Auto-generated method stub
		return tuitionsetMapper.selectByDynamic(record);
	}
	@Override
	public int insert(Tuitionset record) {
		// TODO Auto-generated method stub
		return tuitionsetMapper.insert(record);
	}
	@Override
	public int insertSelective(Tuitionset record) {
		// TODO Auto-generated method stub
		return tuitionsetMapper.insertSelective(record);
	}
	@Override
	public int updateByPrimaryKeySelective(Tuitionset record) {
		// TODO Auto-generated method stub
		return tuitionsetMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int updateByPrimaryKey(Tuitionset record) {
		// TODO Auto-generated method stub
		return tuitionsetMapper.updateByPrimaryKey(record);
	}
	@Override
	public int selCountByTermId(Integer termId) {
		return tuitionsetMapper.selCountByTermId(termId);
	}
	@Override
	public List<Tuitionset> selectByDynamicStatus(Tuitionset record) {
		return tuitionsetMapper.selectByDynamicStatus(record);
	}
	@Override
	public Tuitionset selectByOnlyTuition(Tuitionset record) {
		return tuitionsetMapper.selectByOnlyTuition(record);
	}

}
