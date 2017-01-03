package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StuReplyModelDMapper;
import com.ht.popj.student.StuReplyModelD;
import com.ht.service.student.StuReplyModelDService;

public class StuReplyModelDServiceImpl implements StuReplyModelDService{

	@Autowired
	StuReplyModelDMapper srmdMapper;
	@Override
	public int deleteById(Integer id) {
		return srmdMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StuReplyModelD srmd) {
		return srmdMapper.insertSelective(srmd);
	}

	@Override
	public StuReplyModelD selectById(Integer id) {
		return srmdMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StuReplyModelD srmd) {
		return srmdMapper.updateByPrimaryKeySelective(srmd);
	}

	@Override
	public int delByUpdate(Integer id) {
		return srmdMapper.delByUpdate(id);
	}

	@Override
	public List<StuReplyModelD> selectAll() {
		return srmdMapper.selectAll();
	}

	@Override
	public List<StuReplyModelD> selectByPJ(StuReplyModelD srmd) {
		return srmdMapper.selectByPJ(srmd);
	}

	@Override
	public List<StuReplyModelD> selectBysrmId(Integer srmId) {
		return srmdMapper.selectBysrmId(srmId);
	}

}
