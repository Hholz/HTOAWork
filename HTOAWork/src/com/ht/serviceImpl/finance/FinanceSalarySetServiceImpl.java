package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceSalarysetMapper;
import com.ht.popj.finance.FinanceSalaryset;
import com.ht.service.finance.FinanceSalarySetService;

public class FinanceSalarySetServiceImpl  implements FinanceSalarySetService{
	@Autowired
	FinanceSalarysetMapper fssm;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		fssm.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FinanceSalaryset record) {
		fssm.insertSelective(record);
		return record.getId();
	}

	@Override
	public FinanceSalaryset selectByPrimaryKey(Integer id) {
		return fssm.selectByPrimaryKey(id);
	}

	@Override
	public List<FinanceSalaryset> selectByName(FinanceSalaryset record) {
		return fssm.selectByName(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceSalaryset record) {
		fssm.updateByPrimaryKeySelective(record);
		return 1;
	}

}
