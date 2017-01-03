package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.AttentojiMapper;
import com.ht.popj.dailyWork.Attentoji;
import com.ht.service.dailyWork.AttentojiService;

public class AttentojiServiceImpl implements AttentojiService{

	@Autowired
	AttentojiMapper attentojiMapper;
	
	@Override
	public int insertSelective(Attentoji record) {
		// TODO 自动生成的方法存根
		return attentojiMapper.insertSelective(record);
	}

	@Override
	public List<Attentoji> Attentojiselect(Attentoji record) {
		// TODO 自动生成的方法存根
		return attentojiMapper.Attentojiselect(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Attentoji record) {
		// TODO 自动生成的方法存根
		return attentojiMapper.updateByPrimaryKeySelective(record);
	}

	
	
}
