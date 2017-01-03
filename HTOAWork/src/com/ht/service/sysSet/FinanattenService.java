package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FinanceAttenceset;

public interface FinanattenService {
	
	public int insertSelective(FinanceAttenceset record);

	public List<FinanceAttenceset> financeattensel(FinanceAttenceset record);
	
	public int updateByPrimaryKeySelective(FinanceAttenceset record);
	
	public FinanceAttenceset selectByPrimaryKey(Integer id);
	
	public int deleteByPrimaryKey(Integer id);
	
	public List<FinanceAttenceset> selectidnull(int id);
}
