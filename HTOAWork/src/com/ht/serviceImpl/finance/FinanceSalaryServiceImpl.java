package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceSalaryMapper;
import com.ht.popj.finance.FinanceSalary;
import com.ht.service.finance.FinanceSalaryService;

public class FinanceSalaryServiceImpl implements FinanceSalaryService{

	@Autowired
	FinanceSalaryMapper finanvesalary;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return finanvesalary.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FinanceSalary record) {
		return finanvesalary.insert(record);
	}

	@Override
	public FinanceSalary selectByPrimaryKey(Integer id) {
		return finanvesalary.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(FinanceSalary record) {
		return finanvesalary.updateByPrimaryKey(record);
	}

	@Override
	public List<FinanceSalary> selectAllList(FinanceSalary record) {
		return finanvesalary.selectAllList(record);
	}

	@Override
	public Integer selectSumSalary(String id) {
		return finanvesalary.selectSumSalary(id);
	}

}
