package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.TuitionsetdetailMapper;
import com.ht.popj.finance.Tuitionsetdetail;
import com.ht.service.finance.TuitionsetdetailService;

public class TuitionsetdetailServiceImpl implements TuitionsetdetailService{

	@Autowired
	TuitionsetdetailMapper tuitionsetdetailMapper;
	@Override
	public List<Tuitionsetdetail> selectDynamic(Tuitionsetdetail record) {
		// TODO Auto-generated method stub
		return tuitionsetdetailMapper.selectDynamic(record);
	}
	@Override
	public int insert(Tuitionsetdetail record) {
		// TODO Auto-generated method stub
		return tuitionsetdetailMapper.insert(record);
	}
	@Override
	public int insertSelective(Tuitionsetdetail record) {
		// TODO Auto-generated method stub
		return tuitionsetdetailMapper.insertSelective(record);
	}
	@Override
	public Tuitionsetdetail selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return tuitionsetdetailMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(Tuitionsetdetail record) {
		// TODO Auto-generated method stub
		return tuitionsetdetailMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int updateByPrimaryKey(Tuitionsetdetail record) {
		// TODO Auto-generated method stub
		return tuitionsetdetailMapper.updateByPrimaryKey(record);
	}
	@Override
	public int updateByTuitionKeySelective(Tuitionsetdetail record) {
		// TODO Auto-generated method stub
		return tuitionsetdetailMapper.updateByTuitionKeySelective(record);
	}
	@Override
	public List<Tuitionsetdetail> selectAllDetail(Tuitionsetdetail record) {
		// TODO Auto-generated method stub
		return tuitionsetdetailMapper.selectAllDetail(record);
	}

}
