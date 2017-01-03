package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowOvertimeMapper;
import com.ht.popj.flow.FlowOvertime;
import com.ht.service.flow.FlowOvertimeService;

public class FlowOvertimeServiceImpl implements FlowOvertimeService {
	
	@Autowired
	FlowOvertimeMapper flowovertimemapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowovertimemapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowOvertime record) {
		int a = 1;
		try {
			flowovertimemapper.insertSelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		if (a == 0) {
			return a;
		}
		return record.getId();
	}

	@Override
	public FlowOvertime selectByPrimaryKey(Integer id) {
		return flowovertimemapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowOvertime> selectAll() {
		return flowovertimemapper.selectAll();
	}

	@Override
	public List<FlowOvertime> selectSelective(FlowOvertime record) {
		return flowovertimemapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowOvertime record) {
		int a = 1;
		try {
			flowovertimemapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
