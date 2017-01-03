package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowStudentApplyMapper;
import com.ht.popj.flow.FlowStudentApply;
import com.ht.service.flow.FlowStudentApplyService;

public class FlowStudentApplyServiceImpl implements FlowStudentApplyService {
	
	@Autowired
	FlowStudentApplyMapper flowstudentapplymapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		flowstudentapplymapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowStudentApply record) {
		int a = 1;
		try {
			flowstudentapplymapper.insertSelective(record);

		} catch (Exception e) {
			a = 0;
			// record.toString();//��־���
		}
		if (a == 0) {
			return a;
		}
		return record.getId();
	}

	@Override
	public FlowStudentApply selectByPrimaryKey(Integer id) {
		return flowstudentapplymapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowStudentApply> selectAll() {
		return flowstudentapplymapper.selectAll();
	}

	@Override
	public List<FlowStudentApply> selectSelective(FlowStudentApply record) {
		return flowstudentapplymapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowStudentApply record) {
		int a = 1;
		try {
			flowstudentapplymapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//��־���
		}
		return a;
	}

	@Override
	public List<FlowStudentApply> selectStudentApplyList(FlowStudentApply record) {
		return flowstudentapplymapper.selectStudentApplyList(record);
	}

}
