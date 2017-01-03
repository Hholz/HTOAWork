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
		// TODO 自动生成的方法存根
		return attenstatisMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Attenstatis record) {
		attenstatisMapper.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public List<Attenstatis> Attenstatiselect(Attenstatis record) {
		// TODO 自动生成的方法存根
		return attenstatisMapper.Attenstatiselect(record);
	}

	@Override
	public List<Attenstatis> Attenstatiselandtoji(Attenstatis record) {
		// TODO 自动生成的方法存根
		return attenstatisMapper.Attenstatiselandtoji(record);
	}

	@Override
	public Attenstatis findattenstatis(Attenstatis attenstatis) throws Exception {
		// TODO 自动生成的方法存根
		return attenstatisMapper.findattenstatis(attenstatis);
	}

	@Override
	public void deleteByPrimaryKey2(Attenstatis record) {
		// TODO 自动生成的方法存根
		attenstatisMapper.deleteByPrimaryKey2(record);
	}


	@Override
	public int addinsertlist(List<Attendance_tal> Attendance_tal) {
		// TODO 自动生成的方法存根
		return attenstatisMapper.addinsertlist(Attendance_tal);
	}

	@Override
	public List<Attenstatis> attenrewardsel(Attenstatis record) {
		// TODO 自动生成的方法存根
		return attenstatisMapper.attenrewardsel(record);
	}

}
