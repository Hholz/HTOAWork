package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.HolidayTypeMapper;
import com.ht.popj.dailyWork.HolidayType;
import com.ht.service.dailyWork.HolidaytypeService;

public class HolidayTypeServiceImpl implements HolidaytypeService{
	
	@Autowired
	HolidayTypeMapper htmapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		htmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(HolidayType record) {
		htmapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public HolidayType selectByPrimaryKey(Integer id) {
		
		return htmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<HolidayType> selectList() {
		
		return htmapper.selectList();
	}

	@Override
	public int updateByPrimaryKeySelective(HolidayType record) {
		htmapper.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public List<HolidayType> selectByName(HolidayType name) {
		return htmapper.selectByName(name);
	}

}
