package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.FinanceAttencerewardsetMapper;
import com.ht.popj.sysSet.FinanceAttencerewardset;
import com.ht.service.sysSet.FinattenRewardService;

public class FinattenRewardServiceImpl implements FinattenRewardService{

	@Autowired
	FinanceAttencerewardsetMapper financeAttencerewardsetMapper;
	
	@Override
	public int insertSelective(FinanceAttencerewardset record) {
		// TODO Auto-generated method stub
		return financeAttencerewardsetMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceAttencerewardset record) {
		// TODO Auto-generated method stub
		return financeAttencerewardsetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<FinanceAttencerewardset> finattenrewardsel(
			FinanceAttencerewardset record) {
		// TODO Auto-generated method stub
		return financeAttencerewardsetMapper.finattenrewardsel(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return financeAttencerewardsetMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FinanceAttencerewardset record) {
		// TODO Auto-generated method stub
		return financeAttencerewardsetMapper.insert(record);
	}

	@Override
	public FinanceAttencerewardset selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return financeAttencerewardsetMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(FinanceAttencerewardset record) {
		// TODO Auto-generated method stub
		return financeAttencerewardsetMapper.updateByPrimaryKey(record);
	}

}
