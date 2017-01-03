package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.BasicSalaryMapper;
import com.ht.popj.finance.BasicSalary;
import com.ht.service.finance.BasicSalaryService;

public class BasicSalaryServiceImpl implements BasicSalaryService{

	@Autowired
	BasicSalaryMapper bsMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return bsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(BasicSalary record) {
		return bsMapper.insert(record);
	}

	@Override
	public int insertSelective(BasicSalary record) {
		return bsMapper.insertSelective(record);
	}

	@Override
	public BasicSalary selectByPrimaryKey(Integer id) {
		return bsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BasicSalary record) {
		return bsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BasicSalary record) {
		return bsMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<BasicSalary> selectByDynamic(BasicSalary record) {
		return bsMapper.selectByDynamic(record);
	}

}
