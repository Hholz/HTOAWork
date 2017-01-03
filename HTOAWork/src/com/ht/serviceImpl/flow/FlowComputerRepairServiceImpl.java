package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.popj.flow.FlowComputerRepair;
import com.ht.service.flow.FlowComputerRepairService;

public class FlowComputerRepairServiceImpl implements FlowComputerRepairService {
	
	@Autowired
	FlowComputerRepairService flowcomputerrepairmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowcomputerrepairmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowComputerRepair record) {
		int a = 1;
		try {
			flowcomputerrepairmapper.insertSelective(record);
		} catch (Exception e) {
			a=0;
			//record.toString();//日志输出
		}
		if(a == 0){
			return a;
		}
		return record.getId();
	}

	@Override
	public FlowComputerRepair selectByPrimaryKey(Integer id) {
		return flowcomputerrepairmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowComputerRepair> selectAll() {
		return flowcomputerrepairmapper.selectAll();
	}

	@Override
	public List<FlowComputerRepair> selectSelective(FlowComputerRepair record) {
		return flowcomputerrepairmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowComputerRepair record) {
		int a = 1;
		try {
			flowcomputerrepairmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a=0;
			//record.toString();//日志输出
		}
		return a;
	}

}
