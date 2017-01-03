package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.Flowschedule;

public interface FlowscheduleService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(Flowschedule record);

	Flowschedule selectByPrimaryKey(Integer id);
	
	List<Flowschedule> selectByName(Flowschedule record);
	
	int updateByPrimaryKeySelective(Flowschedule record);
}
