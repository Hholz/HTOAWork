package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowReturnmaterialdetailMapper;
import com.ht.popj.flow.FlowReturnmaterialdetail;
import com.ht.service.flow.FlowReturnmaterialdetailService;

public class FlowReturnmaterialdetailServiceImpl implements FlowReturnmaterialdetailService {
	
	@Autowired
	FlowReturnmaterialdetailMapper flowreturnmaterialdetailmapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowreturnmaterialdetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowReturnmaterialdetail record) {
//		int a = 1;
//		try {
			flowreturnmaterialdetailmapper.insertSelective(record);
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
	public FlowReturnmaterialdetail selectByPrimaryKey(Integer id) {
		return flowreturnmaterialdetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowReturnmaterialdetail> selectAll() {
		return flowreturnmaterialdetailmapper.selectAll();
	}

	@Override
	public List<FlowReturnmaterialdetail> selectSelective(FlowReturnmaterialdetail record) {
		return flowreturnmaterialdetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowReturnmaterialdetail record) {
//		int a = 1;
//		try {
		flowreturnmaterialdetailmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		return a;
		return 1;
	}

	@Override
	public List<FlowReturnmaterialdetail> selectLength(Integer id) {
		return flowreturnmaterialdetailmapper.selectLength(id);
	}

	@Override
	public List<FlowReturnmaterialdetail> selectReturnMateriallist(String empid) {
		return flowreturnmaterialdetailmapper.selectReturnMateriallist(empid);
	}

}
