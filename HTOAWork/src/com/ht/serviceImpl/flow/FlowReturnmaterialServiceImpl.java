package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowReturnmaterialMapper;
import com.ht.popj.flow.FlowReturnmaterial;
import com.ht.service.flow.FlowReturnmaterialService;

public class FlowReturnmaterialServiceImpl implements FlowReturnmaterialService {
	
	@Autowired
	FlowReturnmaterialMapper flowreturnmaterialmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowreturnmaterialmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowReturnmaterial record) {
//		int a = 1;
//		try {
			flowreturnmaterialmapper.insertSelective(record);
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
	public FlowReturnmaterial selectByPrimaryKey(Integer id) {
		return flowreturnmaterialmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowReturnmaterial> selectAll() {
		return flowreturnmaterialmapper.selectAll();
	}

	@Override
	public List<FlowReturnmaterial> selectSelective(FlowReturnmaterial record) {
		return flowreturnmaterialmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowReturnmaterial record) {
//		int a = 1;
//		try {
			flowreturnmaterialmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		return a;
		return 1;
	}

}
