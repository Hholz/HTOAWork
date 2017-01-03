package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowHolidayMapper;
import com.ht.popj.flow.FlowHoliday;
import com.ht.service.flow.FlowHolidayService;

public class FlowHolidayServiceImpl implements FlowHolidayService {
	
	@Autowired
	FlowHolidayMapper flowholidaymapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowholidaymapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowHoliday record) {
		int a = 1;
		try {
			flowholidaymapper.insertSelective(record);
		} catch (Exception e) {
			a=0;
			//record.toString();//日志输出
		}
		if(a == 0){
			return a;
		}
		return record.getId();
	}

	@Override
	public FlowHoliday selectByPrimaryKey(Integer id) {
		return flowholidaymapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowHoliday> selectAll() {
		return flowholidaymapper.selectAll();
	}

	@Override
	public List<FlowHoliday> selectSelective(FlowHoliday record) {
		return flowholidaymapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowHoliday record) {
		int a = 1;
		try {
			flowholidaymapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a=0;
			//record.toString();//日志输出
		}
		return a;
	}

	@Override
	public int insertEmpHoliday(FlowHoliday record) {
//		int a = 1;
//		try {
			flowholidaymapper.insertEmpHoliday(record);
//		} catch (Exception e) {
//			a=0;
//			//record.toString();//日志输出
//		}
//		if(a == 0){
//			return a;
//		}
		return record.getId();
//		return 0;
	}

	@Override
	public List<FlowHoliday> selectMyEmpHoliday(FlowHoliday record) {
		return flowholidaymapper.selectMyEmpHoliday(record);
	}

}
