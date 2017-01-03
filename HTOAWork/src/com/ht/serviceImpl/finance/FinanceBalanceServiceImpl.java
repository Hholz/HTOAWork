package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ht.mapper.finance.FinanceBalanceMapper;
import com.ht.popj.finance.FinanceBalance;
import com.ht.service.finance.FinanceBalanceService;

@Repository
public class FinanceBalanceServiceImpl implements FinanceBalanceService{

	@Autowired
	FinanceBalanceMapper balanceMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return balanceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FinanceBalance record) {
		return balanceMapper.insert(record);
	}

	@Override
	public int insertSelective(FinanceBalance record) {
		return balanceMapper.insertSelective(record);
	}

	@Override
	public FinanceBalance selectByPrimaryKey(Integer id) {
		return balanceMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceBalance record) {
		return balanceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FinanceBalance record) {
		return balanceMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<FinanceBalance> selectFinanceBalanceAll() {
		return balanceMapper.selectFinanceBalanceAll();
	}

	@Override
	public List<FinanceBalance> selectByDynamic(FinanceBalance balance) {
		return balanceMapper.selectByDynamic(balance);
	}

	@Override
	public int getCount() {
		return balanceMapper.getCount();
	}

	@Override
	public List<FinanceBalance> selectByDynamicOfStudent(FinanceBalance balance) {
		return balanceMapper.selectByDynamicOfStudent(balance);
	}

	@Override
	public List<FinanceBalance> statisticsAllFee(FinanceBalance balance) {
		return balanceMapper.statisticsAllFee(balance);
	}

	@Override
	public List<FinanceBalance> selectByDynamicOfBalance(FinanceBalance balance) {
		return balanceMapper.selectByDynamicOfBalance(balance);
	}

}
