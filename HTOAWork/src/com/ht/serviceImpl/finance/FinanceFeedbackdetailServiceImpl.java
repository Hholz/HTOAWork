package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceFeedbackdetailMapper;
import com.ht.popj.finance.FinanceFeedbackdetail;
import com.ht.service.finance.FinanceFeedbackdetailService;

public class FinanceFeedbackdetailServiceImpl implements FinanceFeedbackdetailService{

	@Autowired
	FinanceFeedbackdetailMapper financeFeedbackdetailMapper;
	@Override
	public List<FinanceFeedbackdetail> selectAll() {
		// TODO Auto-generated method stub
		return financeFeedbackdetailMapper.selectAll();
	}
	@Override
	public List<FinanceFeedbackdetail> selectDynamic(
			FinanceFeedbackdetail record) {
		// TODO Auto-generated method stub
		return financeFeedbackdetailMapper.selectDynamic(record);
	}
	@Override
	public int insert(FinanceFeedbackdetail record) {
		// TODO Auto-generated method stub
		return financeFeedbackdetailMapper.insert(record);
	}
	@Override
	public int insertSelective(FinanceFeedbackdetail record) {
		// TODO Auto-generated method stub
		return financeFeedbackdetailMapper.insertSelective(record);
	}
	@Override
	public int updateByPrimaryKeySelective(FinanceFeedbackdetail record) {
		// TODO Auto-generated method stub
		return financeFeedbackdetailMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return financeFeedbackdetailMapper.deleteByPrimaryKey(id);
	}

}
