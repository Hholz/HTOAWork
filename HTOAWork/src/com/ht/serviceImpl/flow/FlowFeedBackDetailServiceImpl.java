package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowFeedBackDetailMapper;
import com.ht.popj.flow.FlowFeedBackDetail;
import com.ht.service.flow.FlowFeedBackDetailService;

public class FlowFeedBackDetailServiceImpl implements FlowFeedBackDetailService {
	
	@Autowired
	FlowFeedBackDetailMapper flowfeedbackdetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowfeedbackdetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowFeedBackDetail record) {
		int a = 1;
		try {
			flowfeedbackdetailmapper.insertSelective(record);
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
	public FlowFeedBackDetail selectByPrimaryKey(Integer id) {
		return flowfeedbackdetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowFeedBackDetail> selectAll(int id) {
		return flowfeedbackdetailmapper.selectAll(id);
	}

	@Override
	public List<FlowFeedBackDetail> selectSelective(FlowFeedBackDetail record) {
		return flowfeedbackdetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowFeedBackDetail record) {
		int a = 1;
		try {
			flowfeedbackdetailmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
