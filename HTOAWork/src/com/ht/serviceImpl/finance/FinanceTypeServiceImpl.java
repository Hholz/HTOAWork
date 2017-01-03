package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceTypeMapper;
import com.ht.popj.finance.FinanceType;
import com.ht.service.finance.FinanceTypeService;


public class FinanceTypeServiceImpl implements FinanceTypeService{

	@Autowired
	FinanceTypeMapper financeTypeMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return financeTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FinanceType record) {
		return financeTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(FinanceType record) {
		return financeTypeMapper.insertSelective(record);
	}

	@Override
	public FinanceType selectByPrimaryKey(Integer id) {
		return financeTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceType record) {
		return financeTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FinanceType record) {
		return financeTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<FinanceType> selectPayTypeAll() {
		return financeTypeMapper.selectPayTypeAll();
	}

	@Override
	public List<FinanceType> selectByDynamic(FinanceType type) {
		return financeTypeMapper.selectByDynamic(type);
	}

	@Override
	public int getCount() {
		return financeTypeMapper.getCount();
	}
	

}
