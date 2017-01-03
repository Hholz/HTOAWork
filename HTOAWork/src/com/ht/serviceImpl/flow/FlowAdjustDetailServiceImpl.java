package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowAdjustDetailMapper;
import com.ht.popj.flow.FlowAdjustDetail;
import com.ht.service.flow.FlowAdjustDetailService;

public class FlowAdjustDetailServiceImpl implements FlowAdjustDetailService {
	
	@Autowired
	FlowAdjustDetailMapper flowadjustdetalmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowadjustdetalmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowAdjustDetail record) {
		int a = 1;
		try {
		flowadjustdetalmapper.insertSelective(record);
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
	public FlowAdjustDetail selectByPrimaryKey(Integer id) {
		return flowadjustdetalmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowAdjustDetail> selectAll() {
		return flowadjustdetalmapper.selectAll();
	}

	@Override
	public List<FlowAdjustDetail> selectSelective(FlowAdjustDetail record) {
		return flowadjustdetalmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowAdjustDetail record) {
		int a = 1;
		try {
			flowadjustdetalmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
