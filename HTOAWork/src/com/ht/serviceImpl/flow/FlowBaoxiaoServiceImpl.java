package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowBaoxiaoMapper;
import com.ht.popj.flow.FlowBaoxiao;
import com.ht.service.flow.FlowBaoxiaoService;

public class FlowBaoxiaoServiceImpl implements FlowBaoxiaoService {

	@Autowired
	FlowBaoxiaoMapper flowbaoxiaomapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowbaoxiaomapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowBaoxiao record) {
//		int a = 1;
//		try {
			flowbaoxiaomapper.insertSelective(record);

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
	public FlowBaoxiao selectByPrimaryKey(Integer id) {
		return flowbaoxiaomapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowBaoxiao> selectAll() {
		return flowbaoxiaomapper.selectAll();
	}

	@Override
	public List<FlowBaoxiao> selectSelective(FlowBaoxiao record) {
		return flowbaoxiaomapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowBaoxiao record) {
		int a = 1;
		try {
			flowbaoxiaomapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

}
