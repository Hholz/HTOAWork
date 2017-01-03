package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowSwaparrangeDetailMapper;
import com.ht.popj.flow.FlowSwaparrangeDetail;
import com.ht.service.flow.FlowSwaparrangeDetailService;

public class FlowSwaparrangeDetailServiceImpl implements FlowSwaparrangeDetailService {
	
	@Autowired
	FlowSwaparrangeDetailMapper flowswaparrangedetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowswaparrangedetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowSwaparrangeDetail record) {
//		int a = 1;
//		try {
			flowswaparrangedetailmapper.insertSelective(record);
//		} catch (Exception e) {
//			a=0;
//			//record.toString();//日志输出
//		}
//		if(a == 0){
//			return a;
//		}
		return record.getId();
	}

	@Override
	public FlowSwaparrangeDetail selectByPrimaryKey(Integer id) {
		return flowswaparrangedetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowSwaparrangeDetail> selectAll() {
		return flowswaparrangedetailmapper.selectAll();
	}

	@Override
	public List<FlowSwaparrangeDetail> selectSelective(FlowSwaparrangeDetail record) {
		return flowswaparrangedetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowSwaparrangeDetail record) {
//		int a = 1;
//		try {
			flowswaparrangedetailmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a=0;
//			//record.toString();//日志输出
//		}
//		return a;
		return 1;
	}

	@Override
	public List<FlowSwaparrangeDetail> selectBySulementId(Integer id) {
		return flowswaparrangedetailmapper.selectBySulementId(id);
	}

}
