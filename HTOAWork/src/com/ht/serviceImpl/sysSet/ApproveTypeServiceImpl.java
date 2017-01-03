package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.ApproveTypeMapper;
import com.ht.popj.sysSet.ApproveType;
import com.ht.service.sysSet.ApproveTypeService;

public class ApproveTypeServiceImpl implements ApproveTypeService{
	
	@Autowired
	ApproveTypeMapper aptypemapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		aptypemapper.deleteByPrimaryKey(id);
		
		return 1;
	}

	@Override
	public int insertSelective(ApproveType record) {
		aptypemapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public List<ApproveType> selectByPrimaryKey(Integer id) {
		return aptypemapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ApproveType> selectByName(ApproveType record) {
		return aptypemapper.selectByName(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ApproveType record) {
		aptypemapper.updateByPrimaryKeySelective(record);
		return 1;
	}

}
