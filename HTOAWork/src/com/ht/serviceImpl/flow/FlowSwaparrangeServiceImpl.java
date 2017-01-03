package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowSwaparrangeMapper;
import com.ht.popj.flow.FlowSwaparrange;
import com.ht.service.flow.FlowSwaparrangeService;

public class FlowSwaparrangeServiceImpl implements FlowSwaparrangeService {
	
	@Autowired
	FlowSwaparrangeMapper flowswaprrangemapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowswaprrangemapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowSwaparrange record) {
//		int a = 1;
//		try {
			flowswaprrangemapper.insertSelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		if (a == 0) {
//			return a;
//		}
		return record.getId();
	}

	@Override
	public FlowSwaparrange selectByPrimaryKey(Integer id) {
		return flowswaprrangemapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowSwaparrange> selectAll() {
		return flowswaprrangemapper.selectAll();
	}

	@Override
	public List<FlowSwaparrange> selectSelective(FlowSwaparrange record) {
		return flowswaprrangemapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowSwaparrange record) {
//		int a = 1;
//		try {
			flowswaprrangemapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		return a;
		return 1;
	}

}
