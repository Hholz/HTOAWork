package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.education.EduOutlineMapper;
import com.ht.popj.education.EduOutline;
import com.ht.popj.education.EduOutlineExample;
import com.ht.service.education.OutlineService;

public class OutlineServiceImpl implements OutlineService{

	@Autowired
	EduOutlineMapper outline;
	
	@Override
	public int countByExample(EduOutlineExample example) {
		return outline.countByExample(example);
	}

	@Override
	public int deleteByExample(EduOutlineExample example) {
		return outline.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return outline.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EduOutline record) {
		return outline.insert(record);
	}

	@Override
	public int insertSelective(EduOutline record) {
		return outline.insertSelective(record);
	}

	@Override
	public List<EduOutline> selectByExample(EduOutlineExample example) {
		return outline.selectByExample(example);
	}

	@Override
	public EduOutline selectByPrimaryKey(Integer id) {
		return outline.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKeySelective(EduOutline record) {
		return outline.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EduOutline record) {
		return outline.updateByPrimaryKey(record);
	}

	@Override
	public List<EduOutline> selectOutlineAll() {
		return outline.selectOutlineAll();
	}

	@Override
	public List<EduOutline> selectByDynamic(EduOutline outlines) {
		return outline.selectByDynamic(outlines);
	}

	@Override
	public int getCount(Integer id) {
		return outline.getCount(id);
	}

	@Override
	public int updateByExampleSelective(EduOutline record, EduOutlineExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(EduOutline record, EduOutlineExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHourSum(EduOutline outlines) {
		return outline.getHourSum(outlines);
	}


}
