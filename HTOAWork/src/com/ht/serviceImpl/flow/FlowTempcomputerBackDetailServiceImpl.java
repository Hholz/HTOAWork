package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowTempcomputerBackDetailMapper;
import com.ht.popj.flow.FlowTempcomputerBackDetail;
import com.ht.service.flow.FlowTempcomputerBackDetailService;

public class FlowTempcomputerBackDetailServiceImpl implements FlowTempcomputerBackDetailService {
	
	@Autowired
	FlowTempcomputerBackDetailMapper flowtempcomputerbackdetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowtempcomputerbackdetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowTempcomputerBackDetail record) {
		int a = 1;
		try {
			flowtempcomputerbackdetailmapper.insertSelective(record);

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
	public FlowTempcomputerBackDetail selectByPrimaryKey(Integer id) {
		return flowtempcomputerbackdetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowTempcomputerBackDetail> selectAll() {
		return flowtempcomputerbackdetailmapper.selectAll();
	}

	@Override
	public List<FlowTempcomputerBackDetail> selectSelective(FlowTempcomputerBackDetail record) {
		return flowtempcomputerbackdetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowTempcomputerBackDetail record) {
		int a = 1;
		try {
			flowtempcomputerbackdetailmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
