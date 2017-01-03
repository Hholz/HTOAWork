package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowHolidaydetailMapper;
import com.ht.popj.flow.FlowHolidaydetail;
import com.ht.service.flow.FlowHolidaydetailService;

public class FlowHolidaydetailServiceImpl implements FlowHolidaydetailService {
	
	@Autowired
	FlowHolidaydetailMapper flowholidaydetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowholidaydetailmapper.deleteByPrimaryKey(id);
		return 1;
	}
	
	public List<FlowHolidaydetail> selectByHolidayid(int holidayid){
		return flowholidaydetailmapper.selectByHolidayid(holidayid);
	}

	@Override
	public int insertSelective(FlowHolidaydetail record) {
		int a = 1;
		try {
			flowholidaydetailmapper.insertSelective(record);
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
	public List<FlowHolidaydetail> selectByPrimaryKey(String empid) {
		return flowholidaydetailmapper.selectByPrimaryKey(empid);
	}

	@Override
	public List<FlowHolidaydetail> selectAll(int id) {
		return flowholidaydetailmapper.selectAll(id);
	}

	@Override
	public List<FlowHolidaydetail> selectSelective(FlowHolidaydetail record) {
		return flowholidaydetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowHolidaydetail record) {
		int a = 1;
		try {
			flowholidaydetailmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a=0;
			//record.toString();//日志输出
		}
		return a;
	}

	@Override
	public List<FlowHolidaydetail> selectEmpHolidayList(String empid) {
		return flowholidaydetailmapper.selectEmpHolidayList(empid);
	}

}
