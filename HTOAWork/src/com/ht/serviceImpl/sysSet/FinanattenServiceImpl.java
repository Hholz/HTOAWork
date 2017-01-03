package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.FinanceAttencesetMapper;
import com.ht.popj.sysSet.FinanceAttenceset;
import com.ht.service.sysSet.FinanattenService;

public class FinanattenServiceImpl implements FinanattenService{

	@Autowired
	FinanceAttencesetMapper financeAttencesetMapper;
	
	@Override
	public int insertSelective(FinanceAttenceset record) {
		// TODO Auto-generated method stub
		return financeAttencesetMapper.insertSelective(record);
	}

	@Override
	public List<FinanceAttenceset> financeattensel(FinanceAttenceset record) {
		// TODO Auto-generated method stub
		return financeAttencesetMapper.financeattensel(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceAttenceset record) {
		// TODO Auto-generated method stub
		return financeAttencesetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public FinanceAttenceset selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return financeAttencesetMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO 自动生成的方法存根
		return financeAttencesetMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<FinanceAttenceset> selectidnull(int id) {
		// TODO 自动生成的方法存根
		return financeAttencesetMapper.selectidnull(id);
	}

}
