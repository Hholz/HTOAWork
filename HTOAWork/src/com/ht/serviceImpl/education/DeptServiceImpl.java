package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ht.mapper.education.EduDeptMapper;
import com.ht.popj.education.EduDept;
import com.ht.popj.education.EduDeptExample;
import com.ht.popj.education.EduMajor;
import com.ht.service.education.DeptService;

@Repository
public class DeptServiceImpl implements DeptService {

	@Autowired
	EduDeptMapper depMapper;
	@Override
	public int countByExample(EduDeptExample example) {
		
		return depMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(EduDeptExample example) {
		return depMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return depMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EduDept record) {
		return depMapper.insert(record);
	}

	@Override
	public int insertSelective(EduDept record) {
		return depMapper.insertSelective(record);
	}

	@Override
	public List<EduDept> selectByExample(EduDeptExample example) {
		return depMapper.selectByExample(example);
	}

	@Override
	public EduDept selectByPrimaryKey(Integer id) {
		return depMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(EduDept record, EduDeptExample example) {
		return depMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(EduDept record, EduDeptExample example) {
		return depMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(EduDept record) {
		return depMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EduDept record) {
		return depMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<EduDept> selectByDynamic(EduDept dept) {
		return depMapper.selectByDynamic(dept);
	}

	@Override
	public List<EduDept> selectDeptAll() {
		return depMapper.selectDeptAll();
	}

}
