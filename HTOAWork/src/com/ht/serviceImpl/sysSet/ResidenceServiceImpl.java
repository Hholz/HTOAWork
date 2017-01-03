package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.ResidenceMapper;
import com.ht.popj.sysSet.Residence;
import com.ht.service.sysSet.ResidenceService;

public class ResidenceServiceImpl implements ResidenceService{

	@Autowired
	ResidenceMapper residenceMapper;
	@Override
	public int deleteById(Integer id) {
		return residenceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(Residence residence) {
		return residenceMapper.insertSelective(residence);
	}

	@Override
	public Residence selectById(Integer id) {
		return residenceMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(Residence residence) {
		return residenceMapper.updateByPrimaryKeySelective(residence);
	}

	@Override
	public int delByUpdate(Integer id) {
		return residenceMapper.delByUpdate(id);
	}

	@Override
	public List<Residence> selectAll() {
		return residenceMapper.selectAll();
	}

}
