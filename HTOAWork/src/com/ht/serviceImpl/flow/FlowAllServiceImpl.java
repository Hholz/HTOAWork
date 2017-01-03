package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowAllMapper;
import com.ht.popj.flow.FlowAll;
import com.ht.service.flow.FlowAllService;

public class FlowAllServiceImpl implements FlowAllService {
	@Autowired
	FlowAllMapper flowallmapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowallmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowAll record) {
//		int a = 1;
//		try {
			flowallmapper.insertSelective(record);
//		} catch (Exception e) {
//			a=0;
//			//record.toString();//日志输出
//		}
//		if(a == 0){
//			return a;
//		}
		return record.getId();
	}

	@Override
	public FlowAll selectByPrimaryKey(Integer id) {
		return flowallmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowAll> selectAll() {
		return flowallmapper.selectAll();
	}

	@Override
	public List<FlowAll> selectSelective(FlowAll record) {
		return flowallmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowAll record) {
//		int a = 1;
//		try {
			flowallmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a=0;
//			//record.toString();//日志输出
//		}
//		return a;
			return 1;
	}

	@Override
	public List<FlowAll> selectFlowAllStud(FlowAll record) {
		return flowallmapper.selectFlowAllStud(record);
	}

	@Override
	public List<FlowAll> selectFlowAllEmp(FlowAll all){
		return flowallmapper.selectFlowAllEmp(all);
	}
	
	@Override
	public List<FlowAll> selectFlowAllEmp1(FlowAll all){
		return flowallmapper.selectFlowAllEmp1(all);
	}
	
	@Override
	public List<FlowAll> selectFlowAllStud1(FlowAll record) {
		return flowallmapper.selectFlowAllStud1(record);
	}

}
