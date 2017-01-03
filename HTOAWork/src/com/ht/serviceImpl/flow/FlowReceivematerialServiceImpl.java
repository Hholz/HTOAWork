package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowReceivematerialMapper;
import com.ht.popj.flow.FlowReceivematerial;
import com.ht.service.flow.FlowReceivematerialService;

public class FlowReceivematerialServiceImpl implements FlowReceivematerialService {
	
	@Autowired
	FlowReceivematerialMapper flowreceivematerialmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowreceivematerialmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowReceivematerial record) {
		int a = 1;
		try {
			flowreceivematerialmapper.insertSelective(record);
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
	public FlowReceivematerial selectByPrimaryKey(Integer id) {
		return flowreceivematerialmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowReceivematerial> selectAll() {
		return flowreceivematerialmapper.selectAll();
	}

	@Override
	public List<FlowReceivematerial> selectSelective(FlowReceivematerial record) {
		return flowreceivematerialmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowReceivematerial record) {
		int a = 1;
		try {
			flowreceivematerialmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
