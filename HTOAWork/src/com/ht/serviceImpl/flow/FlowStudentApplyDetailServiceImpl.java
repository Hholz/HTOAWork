package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowStudentApplyDetailMapper;
import com.ht.popj.flow.FlowStudentApplyDetail;
import com.ht.service.flow.FlowStudentApplyDetailService;

public class FlowStudentApplyDetailServiceImpl implements FlowStudentApplyDetailService {
	
	@Autowired
	FlowStudentApplyDetailMapper flowstudentapplydetailmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowstudentapplydetailmapper.deleteByPrimaryKey(id);
		return 0;
	}

	@Override
	public int insertSelective(FlowStudentApplyDetail record) {
		flowstudentapplydetailmapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public List<FlowStudentApplyDetail> selectByPrimaryKey(Integer id) {
		return flowstudentapplydetailmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowStudentApplyDetail> selectAll(int id) {
		return flowstudentapplydetailmapper.selectAll(id);
	}

	@Override
	public List<FlowStudentApplyDetail> selectSelective(FlowStudentApplyDetail record) {
		return flowstudentapplydetailmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowStudentApplyDetail record) {
		int a = 1;
		try {
			flowstudentapplydetailmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//»’÷æ ‰≥ˆ
		}
		return a;
	}

}
