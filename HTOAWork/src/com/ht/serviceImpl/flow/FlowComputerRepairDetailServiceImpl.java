package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowComputerRepairDetailMapper;
import com.ht.popj.flow.FlowComputerRepairDetail;
import com.ht.service.flow.FlowComputerRepairDetailService;

public class FlowComputerRepairDetailServiceImpl implements FlowComputerRepairDetailService {
	
	
	@Autowired
	FlowComputerRepairDetailMapper flowcomputerrepairdetailmapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowcomputerrepairdetailmapper.deleteByPrimaryKey(id);
		return 0;
	}

	@Override
	public int insertSelective(FlowComputerRepairDetail record) {
		int a = 1;
		try {
			flowcomputerrepairdetailmapper.insertSelective(record);

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
	public FlowComputerRepairDetail selectByPrimaryKey(Integer id) {
		return flowcomputerrepairdetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowComputerRepairDetail> selectAll() {
		return flowcomputerrepairdetailmapper.selectAll();
	}

	@Override
	public List<FlowComputerRepairDetail> selectSelective(FlowComputerRepairDetail record) {
		return flowcomputerrepairdetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowComputerRepairDetail record) {
		int a = 1;
		try {
			flowcomputerrepairdetailmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
