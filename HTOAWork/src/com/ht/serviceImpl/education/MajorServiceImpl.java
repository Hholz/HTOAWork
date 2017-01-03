package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ht.mapper.education.EduMajorMapper;
import com.ht.popj.education.EduMajor;
import com.ht.service.education.MajorService;

@Repository
public class MajorServiceImpl implements MajorService {

	@Autowired
	EduMajorMapper majorMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return majorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EduMajor record) {
		return majorMapper.insert(record);
	}

	@Override
	public int insertSelective(EduMajor record) {
		return majorMapper.insertSelective(record);
	}

	@Override
	public EduMajor selectByPrimaryKey(Integer id) {
		return majorMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(EduMajor record) {
		return majorMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EduMajor record) {
		return majorMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<EduMajor> selectByDynamic(EduMajor major) {
		return majorMapper.selectByDynamic(major);
	}

	@Override
	public List<EduMajor> selectMajorAll() {
		return majorMapper.selectMajorAll();
	}

	@Override
	public int findInfoById(Integer id) {
		return majorMapper.findInfoById(id);
	}

}
