package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.ApprovalTitleMapper;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.service.sysSet.ApprovalTitleService;

public class ApprovalTitleServiceImpl implements ApprovalTitleService{
	@Autowired
	ApprovalTitleMapper appmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		appmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(ApprovalTitle record) {
		appmapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public ApprovalTitle selectByPrimaryKey(Integer id) {
		
		return appmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ApprovalTitle> selectByName(ApprovalTitle record) {
		
		return appmapper.selectByName(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ApprovalTitle record) {
		appmapper.updateByPrimaryKeySelective(record);
		return 1;
	}

}
