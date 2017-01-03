package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinaceFeedbacksetMapper;
import com.ht.popj.finance.FinaceFeedbackset;
import com.ht.service.finance.FinanceFeedbacksetService;

public class FinanceFeedbacksetServiceImpl implements FinanceFeedbacksetService {

	@Autowired FinaceFeedbacksetMapper finaceFeedbacksetMapper;
	@Override
	public List<FinaceFeedbackset> selectFinanceFeedbacksetAll() {
		// TODO Auto-generated method stub
		return finaceFeedbacksetMapper.selectFinanceFeedbacksetAll();
	}

	@Override
	public List<FinaceFeedbackset> selectFinanceFeedbacksetByDynamic(
			FinaceFeedbackset finance) {
		// TODO Auto-generated method stub
		return finaceFeedbacksetMapper.selectFinanceFeedbacksetByDynamic(finance);
	}

	@Override
	public int insert(FinaceFeedbackset finance) {
		// TODO Auto-generated method stub
		return finaceFeedbacksetMapper.insert(finance);
	}

	@Override
	public int updateByPrimaryKeySelective(FinaceFeedbackset record) {
		// TODO Auto-generated method stub
		return finaceFeedbacksetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return finaceFeedbacksetMapper.deleteByPrimaryKey(id);
	}

}
