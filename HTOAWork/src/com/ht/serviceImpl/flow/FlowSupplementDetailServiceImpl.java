package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowSupplementDetailMapper;
import com.ht.popj.flow.FlowSupplementDetail;
import com.ht.popj.flow.FlowSwaparrangeDetail;
import com.ht.service.flow.FlowSupplementDetailService;

public class FlowSupplementDetailServiceImpl implements FlowSupplementDetailService {

	@Autowired
	FlowSupplementDetailMapper flowsupplementdetailmapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowsupplementdetailmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowSupplementDetail record) {
		// int a = 1;
		// try {
		flowsupplementdetailmapper.insertSelective(record);
		// } catch (Exception e) {
		// a=0;
		// //record.toString();//日志输出
		// }
		// if(a == 0){
		// return a;
		// }
		return record.getId();
	}

	@Override
	public FlowSupplementDetail selectByPrimaryKey(Integer id) {
		return flowsupplementdetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowSupplementDetail> selectAll() {
		return flowsupplementdetailmapper.selectAll();
	}

	@Override
	public List<FlowSupplementDetail> selectSelective(FlowSupplementDetail record) {
		return flowsupplementdetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowSupplementDetail record) {
		// int a = 1;
		// try {
		flowsupplementdetailmapper.updateByPrimaryKeySelective(record);
		// } catch (Exception e) {
		// a=0;
		// //record.toString();//日志输出
		// }
		// return a;
		return 1;
	}

	@Override
	public List<FlowSupplementDetail> selectBySulementId(Integer record) {
		return flowsupplementdetailmapper.selectBySulementId(record);
	}

	@Override
	public List<FlowSupplementDetail> selectSupplementList(String empid) {
		return flowsupplementdetailmapper.selectSupplementList(empid);
	}

}
