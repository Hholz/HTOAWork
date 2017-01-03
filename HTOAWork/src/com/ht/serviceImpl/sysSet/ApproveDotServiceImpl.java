package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.ApproveDotMapper;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.service.sysSet.ApproveDotService;

public class ApproveDotServiceImpl implements ApproveDotService {
	@Autowired
	ApproveDotMapper appdotmapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		appdotmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(ApproveDot record) {
		appdotmapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public List<ApproveDot> selectByPrimaryKey(Integer id) {
		return appdotmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ApproveDot> selectByName(ApproveDot id) {
		
		return appdotmapper.selectByName(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ApproveDot record) {
		appdotmapper.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public List<ApproveDot> selectlist() {
		
		return appdotmapper.selectlist();
	}

}
