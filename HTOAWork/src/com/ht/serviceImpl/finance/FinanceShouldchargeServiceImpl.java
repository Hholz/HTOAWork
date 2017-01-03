package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceShouldchargeMapper;
import com.ht.popj.finance.FinanceShouldcharge;
import com.ht.service.finance.FinanceShouldchargeService;

public class FinanceShouldchargeServiceImpl implements FinanceShouldchargeService{

	@Autowired
	FinanceShouldchargeMapper shouldchargeMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return shouldchargeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FinanceShouldcharge record) {
		return shouldchargeMapper.insert(record);
	}

	@Override
	public FinanceShouldcharge selectByPrimaryKey(FinanceShouldcharge record) {
		return shouldchargeMapper.selectByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKey(FinanceShouldcharge record) {
		return shouldchargeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<FinanceShouldcharge> selectAllList(FinanceShouldcharge record) {
		return shouldchargeMapper.selectAllList(record);
	}

	@Override
	public FinanceShouldcharge selectById(Integer id) {
		return shouldchargeMapper.selectById(id);
	}

	@Override
	public List<FinanceShouldcharge> selectByDynamic(FinanceShouldcharge record) {
		return shouldchargeMapper.selectByDynamic(record);
	}

}
