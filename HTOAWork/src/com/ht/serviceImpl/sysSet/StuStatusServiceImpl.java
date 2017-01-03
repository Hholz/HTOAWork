package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.StuStatusMapper;
import com.ht.popj.sysSet.StuStatus;
import com.ht.service.sysSet.StuStatusService;

public class StuStatusServiceImpl implements StuStatusService{

	@Autowired
	StuStatusMapper stuStatusMapper;
	@Override
	public int deleteById(Integer id) {
		return stuStatusMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StuStatus stuStatus) {
		return stuStatusMapper.insertSelective(stuStatus);
	}

	@Override
	public StuStatus selectById(Integer id) {
		return stuStatusMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StuStatus stuStatus) {
		return stuStatusMapper.updateByPrimaryKeySelective(stuStatus);
	}

	@Override
	public int delByUpdate(Integer id) {
		return stuStatusMapper.delByUpdate(id);
	}

	@Override
	public List<StuStatus> selectAll() {
		return stuStatusMapper.selectAll();
	}

}
