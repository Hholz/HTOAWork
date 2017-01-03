package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceFeestandardMapper;
import com.ht.popj.finance.FinanceFeestandard;
import com.ht.service.finance.FinanceFeeStandardService;

public class FinanceFeeStandardServiceImpl implements FinanceFeeStandardService{

	@Autowired
	FinanceFeestandardMapper feeMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return feeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FinanceFeestandard record) {
		return feeMapper.insert(record);
	}

	@Override
	public int insertSelective(FinanceFeestandard record) {
		return feeMapper.insertSelective(record);
	}

	@Override
	public FinanceFeestandard selectByPrimaryKey(Integer id) {
		return feeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceFeestandard record) {
		return feeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FinanceFeestandard record) {
		return feeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<FinanceFeestandard> selectFeeStandardAll() {
		return feeMapper.selectFeeStandardAll();
	}

	@Override
	public List<FinanceFeestandard> selectByDynamic(FinanceFeestandard fee) {
		return feeMapper.selectByDynamic(fee);
	}

	@Override
	public int getCount() {
		return feeMapper.getCount();
	}

	@Override
	public int selectByTypeId(String feeName) {
		return feeMapper.selectByTypeId(feeName);
	}

	@Override
	public FinanceFeestandard selectByPrimaryTypeId(Integer typeId) {
		return feeMapper.selectByPrimaryTypeId(typeId);
	}

}
