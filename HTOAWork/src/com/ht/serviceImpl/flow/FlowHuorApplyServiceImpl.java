package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowHuorApplyMapper;
import com.ht.popj.flow.FlowHuorApply;
import com.ht.service.flow.FlowHuorApplyService;

public class FlowHuorApplyServiceImpl implements FlowHuorApplyService {
	
	@Autowired
	FlowHuorApplyMapper flowhuorapplymapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowhuorapplymapper.deleteByPrimaryKey(id);
		return 0;
	}

	@Override
	public int insertSelective(FlowHuorApply record) {
		int a = 1;
		try {
			flowhuorapplymapper.insertSelective(record);

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
	public FlowHuorApply selectByPrimaryKey(Integer id) {
		return flowhuorapplymapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowHuorApply> selectAll() {
		return flowhuorapplymapper.selectAll();
	}

	@Override
	public List<FlowHuorApply> selectSelective(FlowHuorApply record) {
		return flowhuorapplymapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowHuorApply record) {
		int a = 1;
		try {
			flowhuorapplymapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
