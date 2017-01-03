package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowApplyMaterialdetailMapper;
import com.ht.popj.flow.FlowApplyMaterialdetail;
import com.ht.service.flow.FlowApplyMaterialdetailService;

public class FlowApplyMaterialdetailServiceImpl implements FlowApplyMaterialdetailService {
	
	@Autowired
	FlowApplyMaterialdetailMapper flowapplymaterialdetailmapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowapplymaterialdetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowApplyMaterialdetail record) {
		int a = 1;
		try {
			flowapplymaterialdetailmapper.insertSelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		if (a == 0) {
			return a;
		}
//		return flowapplymaterialdetailmapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public FlowApplyMaterialdetail selectByPrimaryKey(Integer id) {
		return flowapplymaterialdetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowApplyMaterialdetail> selectAll() {
		return flowapplymaterialdetailmapper.selectAll();
	}

	@Override
	public List<FlowApplyMaterialdetail> selectSelective(FlowApplyMaterialdetail record) {
		return flowapplymaterialdetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowApplyMaterialdetail record) {
//		int a = 1;
//		try {
//			flowapplymaterialdetailmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		return a;
			return flowapplymaterialdetailmapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<FlowApplyMaterialdetail> selectTaskList(String record) {
		List<FlowApplyMaterialdetail> list = flowapplymaterialdetailmapper.selectTaskList(record);
		return list;
	}

	@Override
	public List<FlowApplyMaterialdetail> selectlength(Integer record) {
		return flowapplymaterialdetailmapper.selectlength(record);
	}

}
