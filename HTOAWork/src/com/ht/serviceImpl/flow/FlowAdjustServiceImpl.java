package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowAdjustMapper;
import com.ht.popj.flow.FlowAdjust;
import com.ht.service.flow.FlowAdjustService;

public class FlowAdjustServiceImpl implements FlowAdjustService {

	@Autowired
	FlowAdjustMapper flowadjustmapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowadjustmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowAdjust record) {
		int a = 1;
		try {
			flowadjustmapper.insertSelective(record);
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
	public FlowAdjust selectByPrimaryKey(Integer id) {
		return flowadjustmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowAdjust> selectAll() {
		return flowadjustmapper.selectAll();
	}

	@Override
	public List<FlowAdjust> selectSelective(FlowAdjust record) {
		return flowadjustmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowAdjust record) {
		int a = 1;
		try {
			flowadjustmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
