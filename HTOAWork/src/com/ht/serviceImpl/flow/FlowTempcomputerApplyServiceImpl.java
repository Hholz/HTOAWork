package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowTempcomputerApplyMapper;
import com.ht.popj.flow.FlowTempcomputerApply;
import com.ht.service.flow.FlowTempcomputerApplyService;

public class FlowTempcomputerApplyServiceImpl implements FlowTempcomputerApplyService {
	
	
	@Autowired
	FlowTempcomputerApplyMapper flowtempcomputerapplymapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowtempcomputerapplymapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowTempcomputerApply record) {
		int a = 1;
		try {
			flowtempcomputerapplymapper.insertSelective(record);
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
	public FlowTempcomputerApply selectByPrimaryKey(Integer id) {
		return flowtempcomputerapplymapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowTempcomputerApply> selectAll() {
		return flowtempcomputerapplymapper.selectAll();
	}

	@Override
	public List<FlowTempcomputerApply> selectSelective(FlowTempcomputerApply record) {
		return flowtempcomputerapplymapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowTempcomputerApply record) {
		int a = 1;
		try {
			flowtempcomputerapplymapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
