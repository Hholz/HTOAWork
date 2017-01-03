package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowModelselMapper;
import com.ht.popj.flow.FlowModelsel;
import com.ht.service.flow.FlowModelselService;

public class FlowModelselServiceImpl implements FlowModelselService {

	@Autowired
	FlowModelselMapper flowmodelselmapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowmodelselmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowModelsel record) {
		int a = 1;
		try {
			flowmodelselmapper.insertSelective(record);

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
	public FlowModelsel selectByPrimaryKey(Integer id) {

		return flowmodelselmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowModelsel> selectAll() {
		return flowmodelselmapper.selectAll();
	}

	@Override
	public List<FlowModelsel> selectSelective(FlowModelsel record) {
		return flowmodelselmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowModelsel record) {
		int a = 1;
		try {
			flowmodelselmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
