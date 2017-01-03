package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.BaoxiaoMapper;
import com.ht.popj.dailyWork.Baoxiao;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.BaoxiaoService;

public class BaoxiaoServiceImpl implements BaoxiaoService{

	@Autowired
	BaoxiaoMapper baoxiaoMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return baoxiaoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Baoxiao record) {
		return baoxiaoMapper.insert(record);
	}

	@Override
	public Baoxiao selectByPrimaryKey(Integer id) {
		return baoxiaoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Baoxiao record) {
		return baoxiaoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Baoxiao> selectList(Baoxiao record) {
		return baoxiaoMapper.selectList(record);
	}

	@Override
	public List<Baoxiao> selectBaoxiaoTask(Emp id) {
		return baoxiaoMapper.selectBaoxiaoTask(id);
	}

	@Override
	public List<Baoxiao> selectBaoxiaoOvertask(Emp id) {
		return baoxiaoMapper.selectBaoxiaoOvertask(id);
	}

}
