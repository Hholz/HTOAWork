package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.HolidayMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Holiday;
import com.ht.service.dailyWork.HolidayService;

public class HolidayServiceImpl implements HolidayService{
	@Autowired
	HolidayMapper holidaymapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		holidaymapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(Holiday record) {
		holidaymapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public Holiday selectByPrimaryKey(Integer id) {
		return holidaymapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Holiday> selectByName(Holiday record) {
		return holidaymapper.selectByName(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Holiday record) {
		holidaymapper.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public List<Holiday> selectHoliday(Holiday record) {
		return holidaymapper.selectHoliday(record);
	}

	@Override
	public Integer selectTask(Emp e) {
		return holidaymapper.selectTask(e);
	}

	@Override
	public Integer selectOverTask(Emp e) {
		
		return holidaymapper.selectOverTask(e);
	}

	@Override
	public Integer selectChangeTask(Emp e) {
		
		return holidaymapper.selectChangeTask(e);
	}

}
