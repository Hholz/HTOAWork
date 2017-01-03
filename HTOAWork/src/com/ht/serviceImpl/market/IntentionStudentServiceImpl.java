package com.ht.serviceImpl.market;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.market.IntentionStudentMapper;
import com.ht.popj.market.IntentionStudent;
import com.ht.service.market.IntentionStudentService;

public class IntentionStudentServiceImpl implements IntentionStudentService{
	@Autowired
	private IntentionStudentMapper intenstuMapper;

	@Override
	public List<IntentionStudent> findIntentionStudentList1(IntentionStudent intenstu) {
		return intenstuMapper.findIntentionStudentList1(intenstu);
	}

	@Override
	public List<IntentionStudent> findIntentionStudentList2() {
		return intenstuMapper.findIntentionStudentList2();
	}

	@Override
	public int addIntentionStudent(IntentionStudent intenstu) {
		return intenstuMapper.addIntentionStudent(intenstu);
	}

	@Override
	public int updateIntentionStudent(IntentionStudent intenstu) {
		return intenstuMapper.updateIntentionStudent(intenstu);
	}

	@Override
	public int deleteIntentionStudentById(Integer id) {
		return intenstuMapper.deleteIntentionStudentById(id);
	}

	@Override
	public IntentionStudent selectByPrimaryKey(Integer id) {
		return intenstuMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<IntentionStudent> findIntentionStudentList3() {
		return intenstuMapper.findIntentionStudentList3();
	}

	@Override
	public int deleteIntentionStudent(Integer id) {
		return intenstuMapper.deleteIntentionStudent(id);
	}

}
