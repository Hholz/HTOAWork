package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowComputerApplyMapper;
import com.ht.popj.flow.FlowComputerApply;
import com.ht.service.flow.FlowComputerApplyService;

public class FlowComputerApplyServiceImpl implements FlowComputerApplyService{

	@Autowired
	private FlowComputerApplyMapper applyMapper;

	@Override
	public int insertSelective(FlowComputerApply record) {
		return applyMapper.insertSelective(record);
	}

	@Override
	public List<FlowComputerApply> selectSelective(FlowComputerApply record) {
		return applyMapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowComputerApply record) {
		return applyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<FlowComputerApply> selectAllComputerApply(FlowComputerApply computerApply) {
		return applyMapper.selectAllComputerApply(computerApply);
	}
}
