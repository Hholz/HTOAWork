package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.SalaryListMapper;
import com.ht.popj.finance.SalaryList;
import com.ht.service.finance.SalaryListService;

public class SalaryListServiceImpl implements SalaryListService{

	@Autowired
	SalaryListMapper salaryMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return salaryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SalaryList record) {
		// TODO Auto-generated method stub
		return salaryMapper.insert(record);
	}

	@Override
	public int insertSelective(SalaryList record) {
		// TODO Auto-generated method stub
		return salaryMapper.insertSelective(record);
	}

	@Override
	public SalaryList selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return salaryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SalaryList record) {
		// TODO Auto-generated method stub
		return salaryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SalaryList record) {
		// TODO Auto-generated method stub
		return salaryMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SalaryList> selectByDynamic(SalaryList record) {
		// TODO Auto-generated method stub
		return salaryMapper.selectByDynamic(record);
	}

	@Override
	public int insertMuch(List<SalaryList> salArr) {
		// TODO Auto-generated method stub
		return salaryMapper.insertMuch(salArr);
	}

}
