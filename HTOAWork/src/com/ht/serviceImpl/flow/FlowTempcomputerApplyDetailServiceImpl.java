package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowTempcomputerApplyDetailMapper;
import com.ht.popj.flow.FlowTempcomputerApplyDetail;
import com.ht.service.flow.FlowTempcomputerApplyDetailService;

public class FlowTempcomputerApplyDetailServiceImpl implements FlowTempcomputerApplyDetailService {
	
	@Autowired
	FlowTempcomputerApplyDetailMapper flowtempcomputerapplydetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowtempcomputerapplydetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowTempcomputerApplyDetail record) {
		int a = 1;
		try {
			flowtempcomputerapplydetailmapper.insertSelective(record);

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
	public FlowTempcomputerApplyDetail selectByPrimaryKey(Integer id) {
		return flowtempcomputerapplydetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowTempcomputerApplyDetail> selectAll() {
		return flowtempcomputerapplydetailmapper.selectAll();
	}

	@Override
	public List<FlowTempcomputerApplyDetail> selectSelective(FlowTempcomputerApplyDetail record) {
		return flowtempcomputerapplydetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowTempcomputerApplyDetail record) {
		int a = 1;
		try {
			flowtempcomputerapplydetailmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
