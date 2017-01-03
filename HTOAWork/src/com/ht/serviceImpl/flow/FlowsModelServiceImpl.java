package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowsModelMapper;
import com.ht.popj.flow.FlowsModel;
import com.ht.service.flow.FlowsModelService;

public class FlowsModelServiceImpl implements FlowsModelService {
	
	@Autowired
	FlowsModelMapper flowsmodelmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowsmodelmapper.deleteByPrimaryKey(id);
		return 1;
	}
	
	@Override
	public void deleteByPrimary(Integer id) {
		flowsmodelmapper.deleteByPrimary(id);
	}

	@Override
	public int insertSelective(FlowsModel record) {
//		int a = 1;
//		try {
//			flowsmodelmapper.insertSelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		if (a == 0) {
//			return a;
//		}
//		return record.getId();
		return flowsmodelmapper.insertSelective(record);
	}

	@Override
	public FlowsModel selectByPrimaryKey(Integer id) {
		return flowsmodelmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowsModel> selectAll(int id) {
		return flowsmodelmapper.selectAll(id);
	}
	
	@Override
	public List<FlowsModel> selectAll2(int id) {
		return flowsmodelmapper.selectAll2(id);
	}

	@Override
	public List<FlowsModel> selectSelective(FlowsModel record) {
		return flowsmodelmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowsModel record) {
		int a = 1;
		try {
			flowsmodelmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}


}
