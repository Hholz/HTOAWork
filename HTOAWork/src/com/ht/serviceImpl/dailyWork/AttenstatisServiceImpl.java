package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.AttenstatisMapper;
import com.ht.popj.dailyWork.Attendance_tal;
import com.ht.popj.dailyWork.Attenstatis;
import com.ht.service.dailyWork.AttenstatisService;

public class AttenstatisServiceImpl implements AttenstatisService{

	@Autowired
	AttenstatisMapper attenstatisMapper;
	
	@Override
	public int insertSelective(Attenstatis record) {
		// TODO �Զ����ɵķ������
		return attenstatisMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Attenstatis record) {
		attenstatisMapper.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public List<Attenstatis> Attenstatiselect(Attenstatis record) {
		// TODO �Զ����ɵķ������
		return attenstatisMapper.Attenstatiselect(record);
	}

	@Override
	public List<Attenstatis> Attenstatiselandtoji(Attenstatis record) {
		// TODO �Զ����ɵķ������
		return attenstatisMapper.Attenstatiselandtoji(record);
	}

	@Override
	public Attenstatis findattenstatis(Attenstatis attenstatis) throws Exception {
		// TODO �Զ����ɵķ������
		return attenstatisMapper.findattenstatis(attenstatis);
	}

	@Override
	public void deleteByPrimaryKey2(Attenstatis record) {
		// TODO �Զ����ɵķ������
		attenstatisMapper.deleteByPrimaryKey2(record);
	}


	@Override
	public int addinsertlist(List<Attendance_tal> Attendance_tal) {
		// TODO �Զ����ɵķ������
		return attenstatisMapper.addinsertlist(Attendance_tal);
	}

	@Override
	public List<Attenstatis> attenrewardsel(Attenstatis record) {
		// TODO �Զ����ɵķ������
		return attenstatisMapper.attenrewardsel(record);
	}

}
