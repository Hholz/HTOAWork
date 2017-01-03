package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.FinanceSalarytypeseMapper;
import com.ht.popj.sysSet.FinanceSalarytypese;
import com.ht.service.sysSet.SalTypeService;

public class SalTypeServiceImpl implements SalTypeService{

	@Autowired
	FinanceSalarytypeseMapper salTypeMapper;

	@Override
	public int deleteById(Integer id) {
		return salTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(FinanceSalarytypese saltype) {
		return salTypeMapper.insertSelective(saltype);
	}

	@Override
	public FinanceSalarytypese selectById(Integer id) {
		return salTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(FinanceSalarytypese saltype) {
		return salTypeMapper.updateByPrimaryKeySelective(saltype);
	}

	@Override
	public int delByUpdate(Integer id) {
		return salTypeMapper.delByUpdate(id);
	}

	@Override
	public List<FinanceSalarytypese> selectAll() {
		return salTypeMapper.selectAll();
	}
}
