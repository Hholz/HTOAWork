package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowTempcomputerBackMapper;
import com.ht.popj.flow.FlowTempcomputerBack;
import com.ht.service.flow.FlowTempcomputerBackService;

public class FlowTempcomputerBackServiceImpl implements FlowTempcomputerBackService {
	
	@Autowired
	FlowTempcomputerBackMapper flowtempcomputerbackmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowtempcomputerbackmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowTempcomputerBack record) {
		int a = 1;
		try {
			flowtempcomputerbackmapper.insertSelective(record);
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
	public FlowTempcomputerBack selectByPrimaryKey(Integer id) {
		return flowtempcomputerbackmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowTempcomputerBack> selectAll() {
		return flowtempcomputerbackmapper.selectAll();
	}

	@Override
	public List<FlowTempcomputerBack> selectSelective(FlowTempcomputerBack record) {
		return flowtempcomputerbackmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowTempcomputerBack record) {
		int a = 1;
		try {
			flowtempcomputerbackmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a=0;
			//record.toString();//日志输出
		}
		return a;
	}

}
