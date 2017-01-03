package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.FlowscheduleMapper;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.sysSet.FlowscheduleService;

public class FlowscheduleServiceImpl implements FlowscheduleService{
	
	@Autowired
	FlowscheduleMapper fm;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		fm.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(Flowschedule record) {
		fm.insertSelective(record);
		return record.getId();
	}

	@Override
	public Flowschedule selectByPrimaryKey(Integer id) {
		
		return fm.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Flowschedule record) {
		fm.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public List<Flowschedule> selectByName(Flowschedule record) {
		return fm.selectByName(record);
	}

}
