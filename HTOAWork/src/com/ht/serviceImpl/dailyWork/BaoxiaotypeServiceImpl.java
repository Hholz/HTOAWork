package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.BaoxiaotypeMapper;
import com.ht.popj.dailyWork.Baoxiaotype;
import com.ht.service.dailyWork.BaoxiaotypeService;

public class BaoxiaotypeServiceImpl  implements BaoxiaotypeService{

	@Autowired
	BaoxiaotypeMapper baoxiaotypeMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return baoxiaotypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Baoxiaotype record) {
		return baoxiaotypeMapper.insert(record);
	}

	@Override
	public Baoxiaotype selectByPrimaryKey(Integer id) {
		return baoxiaotypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Baoxiaotype record) {
		return baoxiaotypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Baoxiaotype> Baoxiaotypelist(Baoxiaotype record) {
		return baoxiaotypeMapper.Baoxiaotypelist(record);
	}

}
