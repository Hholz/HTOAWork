package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowModeltypeMapper;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.service.flow.FlowModeltypeService;

public class FlowModeltypeServiceImpl implements FlowModeltypeService {

	@Autowired
	FlowModeltypeMapper flowmodeltypemapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowmodeltypemapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowsModeltype record) {
		int a = 1;
		try {
		flowmodeltypemapper.insertSelective(record);
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
	public FlowsModeltype selectByPrimaryKey(Integer id) {
		return flowmodeltypemapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowsModeltype> selectAll(int id) {
		return flowmodeltypemapper.selectAll(id);
	}

	@Override
	public List<FlowsModeltype> selectSelective(FlowsModeltype record) {
		return flowmodeltypemapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowsModeltype record) {
		int a = 1;
		try {
			flowmodeltypemapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}
	
	public List<FlowsModeltype> selectModelTypeByModelSelId(String modelSelName){
		return flowmodeltypemapper.selectModelTypeByModelSelId(modelSelName);
	}
}
