package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowFeedBackMapper;
import com.ht.popj.flow.FlowFeedBack;
import com.ht.service.flow.FlowFeedBackService;

public class FlowFeedBackServiceImpl implements FlowFeedBackService {
	
	@Autowired
	FlowFeedBackMapper flowfeedbackmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowfeedbackmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowFeedBack record) {
		int a = 1;
		try {
			flowfeedbackmapper.insertSelective(record);
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
	public FlowFeedBack selectByPrimaryKey(Integer id) {
		return flowfeedbackmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowFeedBack> selectAll() {
		return flowfeedbackmapper.selectAll();
	}

	@Override
	public List<FlowFeedBack> selectSelective(FlowFeedBack record) {
		return flowfeedbackmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowFeedBack record) {
		int a = 1;
		try {
			flowfeedbackmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
