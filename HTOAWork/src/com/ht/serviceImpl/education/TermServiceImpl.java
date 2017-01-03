package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.education.EduTermMapper;
import com.ht.popj.education.EduTerm;
import com.ht.popj.education.EduTermExample;
import com.ht.service.education.TermService;

public class TermServiceImpl implements TermService{

	@Autowired
	EduTermMapper termMapper;
	
	@Override
	public int countByExample(EduTermExample example) {
		return termMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(EduTermExample example) {
		return termMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return termMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EduTerm record) {
		return termMapper.insert(record);
	}

	@Override
	public int insertSelective(EduTerm record) {
		return termMapper.insertSelective(record);
	}

	@Override
	public List<EduTerm> selectByExample(EduTermExample example) {
		return termMapper.selectByExample(example);
	}

	@Override
	public EduTerm selectByPrimaryKey(Integer id) {
		return termMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(EduTerm record, EduTermExample example) {
		return termMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(EduTerm record, EduTermExample example) {
		return termMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(EduTerm record) {
		return termMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EduTerm record) {
		return termMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<EduTerm> selectTermAll() {
		return termMapper.selectTermAll();
	}

	@Override
	public List<EduTerm> selectByDynamic(EduTerm term) {
		return termMapper.selectByDynamic(term);
	}

	@Override
	public List<EduTerm> selectByAllMajor(EduTerm term) {
		// TODO Auto-generated method stub
		return termMapper.selectByAllMajor(term);
	}

}
