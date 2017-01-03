package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.SysHolidayMapper;
import com.ht.popj.sysSet.SysHoliday;
import com.ht.service.sysSet.SetholidayService;

public class SetholidayServiceImpl implements SetholidayService {

	@Autowired
	SysHolidayMapper sysHolidayMapper;
	
	@Override
	public int insertSelective(SysHoliday record) {
		// TODO 自动生成的方法存根
		return sysHolidayMapper.insertSelective(record);
	}

	@Override
	public SysHoliday selectByPrimaryKey(Integer id) {
		// TODO 自动生成的方法存根
		return sysHolidayMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysHoliday record) {
		// TODO 自动生成的方法存根
		return sysHolidayMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<SysHoliday> setholidayselect(SysHoliday record) {
		// TODO 自动生成的方法存根
		return sysHolidayMapper.setholidayselect(record);
	}

}
