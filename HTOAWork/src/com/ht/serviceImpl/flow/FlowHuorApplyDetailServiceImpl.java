package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowHuorApplyDetailMapper;
import com.ht.popj.flow.FlowHuorApplyDetail;
import com.ht.service.flow.FlowHuorApplyDetailService;

public class FlowHuorApplyDetailServiceImpl implements FlowHuorApplyDetailService {
	
	@Autowired
	FlowHuorApplyDetailMapper flowhuorapplydetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowhuorapplydetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowHuorApplyDetail record) {
		int a = 1;
		try {
			flowhuorapplydetailmapper.insertSelective(record);

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
	public FlowHuorApplyDetail selectByPrimaryKey(Integer id) {
		return flowhuorapplydetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowHuorApplyDetail> selectAll() {
		return flowhuorapplydetailmapper.selectAll();
	}

	@Override
	public List<FlowHuorApplyDetail> selectSelective(FlowHuorApplyDetail record) {
		return flowhuorapplydetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowHuorApplyDetail record) {
		int a = 1;
		try {
			flowhuorapplydetailmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
