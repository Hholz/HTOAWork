package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceSalarylistMapper;
import com.ht.popj.finance.FinanceSalarylist;
import com.ht.service.finance.FinanceSalarylistService;

public class FinanceSalarylistServiceServiceImpl implements FinanceSalarylistService {
	
	@Autowired
	FinanceSalarylistMapper financesalarylistmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		financesalarylistmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FinanceSalarylist record) {
		int a = 1;
		try {
			financesalarylistmapper.insertSelective(record);
		} catch (Exception e) {
			a=0;
			//record.toString();//日志输出
		}
		if(a == 0){
			return a;
		}
		return record.getId();
	}

	@Override
	public FinanceSalarylist selectByPrimaryKey(Integer id) {
		return financesalarylistmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FinanceSalarylist> selectAll() {
		return financesalarylistmapper.selectAll();
	}

	@Override
	public List<FinanceSalarylist> selectSelective(FinanceSalarylist record) {
		return financesalarylistmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceSalarylist record) {
//		int a = 1;
//		try {
			financesalarylistmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a=0;
//			//record.toString();//日志输出
//		}
		return 1;
	}

}
