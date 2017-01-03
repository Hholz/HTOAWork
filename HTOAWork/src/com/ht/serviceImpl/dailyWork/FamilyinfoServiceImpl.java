package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.FamilyinfoMapper;
import com.ht.popj.dailyWork.Familyinfo;
import com.ht.service.dailyWork.FamilyinfoService;

public class FamilyinfoServiceImpl implements FamilyinfoService{

	@Autowired
	FamilyinfoMapper familyinfoMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return familyinfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Familyinfo record) {
		familyinfoMapper.insert(record);
		return record.getId();
	}

	@Override
	public Familyinfo selectByPrimaryKey(Integer id) {
		return familyinfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Familyinfo record) {
		return familyinfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Familyinfo> selectFamilyinfo(Familyinfo record) {
		return familyinfoMapper.selectFamilyinfo(record);
	}

}
