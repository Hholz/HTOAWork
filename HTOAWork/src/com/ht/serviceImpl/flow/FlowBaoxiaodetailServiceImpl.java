package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowBaoxiaodetailMapper;
import com.ht.popj.flow.FlowBaoxiaodetail;
import com.ht.service.flow.FlowBaoxiaodetailService;

public class FlowBaoxiaodetailServiceImpl implements FlowBaoxiaodetailService {
	
	@Autowired
	FlowBaoxiaodetailMapper flowbaoxiaodetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowbaoxiaodetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowBaoxiaodetail record) {
//		int a = 1;
//		try {
			flowbaoxiaodetailmapper.insertSelective(record);

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
	public FlowBaoxiaodetail selectByPrimaryKey(Integer id) {
		return flowbaoxiaodetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowBaoxiaodetail> selectAll() {
		return flowbaoxiaodetailmapper.selectAll();
	}

	@Override
	public List<FlowBaoxiaodetail> selectSelective(FlowBaoxiaodetail record) {
		return flowbaoxiaodetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowBaoxiaodetail record) {
//		int a = 1;
//		try {
			flowbaoxiaodetailmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		return a;
			return 1;
	}

	@Override
	public List<FlowBaoxiaodetail> selectlength(Integer baoxiaoid) {
		return flowbaoxiaodetailmapper.selectlength(baoxiaoid);
	}

	@Override
	public List<FlowBaoxiaodetail> selectBaoxiaodetaillist(String empid) {
		return flowbaoxiaodetailmapper.selectBaoxiaodetaillist(empid);
	}

}
