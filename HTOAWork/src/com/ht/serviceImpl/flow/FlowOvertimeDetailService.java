package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowOvertimeDetailMapper;
import com.ht.popj.flow.FlowOvertimeDetail;

public class FlowOvertimeDetailService implements com.ht.service.flow.FlowOvertimeDetailService {
	
	@Autowired
	FlowOvertimeDetailMapper flowovertimedetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowovertimedetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowOvertimeDetail record) {
		int a = 1;
		try {
			flowovertimedetailmapper.insertSelective(record);

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
	public FlowOvertimeDetail selectByPrimaryKey(Integer id) {
		return flowovertimedetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowOvertimeDetail> selectAll() {
		return flowovertimedetailmapper.selectAll();
	}

	@Override
	public List<FlowOvertimeDetail> selectSelective(FlowOvertimeDetail record) {
		return flowovertimedetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowOvertimeDetail record) {
		int a = 1;
		try {
			flowovertimedetailmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
