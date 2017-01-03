package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowApplyMaterialMapper;
import com.ht.popj.flow.FlowApplyMaterial;
import com.ht.service.flow.FlowApplyMaterialService;

public class FlowApplyMaterialServiceImpl implements FlowApplyMaterialService {
	
	@Autowired
	FlowApplyMaterialMapper flowapplymaterialmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowapplymaterialmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowApplyMaterial record) {
//		int a = 1;
//		try {
			flowapplymaterialmapper.insertSelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		if (a == 0) {
//			return a;
//		}
//		return record.getId();
			return record.getId();
	}

	@Override
	public FlowApplyMaterial selectByPrimaryKey(Integer id) {
		return flowapplymaterialmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowApplyMaterial> selectAll() {
		return flowapplymaterialmapper.selectAll();
	}

	@Override
	public List<FlowApplyMaterial> selectSelective(FlowApplyMaterial record) {
		return flowapplymaterialmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowApplyMaterial record) {
//		int a = 1;
//		try {
			flowapplymaterialmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		return a;
		return 1;
	}

}
