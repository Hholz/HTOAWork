package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowSupplementMapper;
import com.ht.popj.flow.FlowSupplement;
import com.ht.service.flow.FlowSupplementService;

public class FlowSupplementServiceImpl implements FlowSupplementService {
	
	@Autowired
	FlowSupplementMapper flowsupplementmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowsupplementmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowSupplement record) {
//		int a = 1;
//		try {
			flowsupplementmapper.insertSelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		if (a == 0) {
//			return a;
//		}
		return record.getId();
	}

	@Override
	public FlowSupplement selectByPrimaryKey(Integer id) {
		return flowsupplementmapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowSupplement record) {
//		int a = 1;
//		try {
			flowsupplementmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		return a;
		return 1;
	}

	@Override
	public List<FlowSupplement> selectAll() {
		return flowsupplementmapper.selectAll();
	}

	@Override
	public List<FlowSupplement> selectSelective(FlowSupplement record) {
		return flowsupplementmapper.selectSelective(record);
	}

	@Override
	public List<FlowSupplement> selectMySupplement(FlowSupplement record) {
		return flowsupplementmapper.selectMySupplement(record);
	}

}
