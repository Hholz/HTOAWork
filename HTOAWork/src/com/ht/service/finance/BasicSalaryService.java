package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.BasicSalary;

public interface BasicSalaryService {
	int deleteByPrimaryKey(Integer id);

	int insert(BasicSalary record);

	int insertSelective(BasicSalary record);

	BasicSalary selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BasicSalary record);

	int updateByPrimaryKey(BasicSalary record);

	List<BasicSalary> selectByDynamic(BasicSalary record);
}
