package com.ht.serviceImpl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.FinanceTuitionsetMapper;
import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;
import com.ht.popj.finance.FinanceTuitionset;
import com.ht.service.finance.FinanceTuitionSetService;

public class FinanceTuitionSetServiceImpl implements FinanceTuitionSetService{

	@Autowired
	FinanceTuitionsetMapper financeTuitionsetMapper;

	@Override
	public int insert(FinanceTuitionset record) {
		// TODO Auto-generated method stub
		return financeTuitionsetMapper.insert(record);
	}

	@Override
	public int insertSelective(FinanceTuitionset record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FinanceTuitionset> selectAll() {
		// TODO Auto-generated method stub
		return financeTuitionsetMapper.selectAll();
	}

	@Override
	public List<FinanceTuitionset> selectDinamic(FinanceTuitionset stu) {
		// TODO Auto-generated method stub
		return financeTuitionsetMapper.selectDinamic(stu);
	}

	@Override
	public List<EduMajor> selectAllMajor() {
		// TODO Auto-generated method stub
		return financeTuitionsetMapper.selectAllMajor();
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return financeTuitionsetMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceTuitionset record) {
		// TODO Auto-generated method stub
		return financeTuitionsetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FinanceTuitionset record) {
		// TODO Auto-generated method stub
		return financeTuitionsetMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<EduTerm> selectAllterm() {
		// TODO Auto-generated method stub
		return financeTuitionsetMapper.selectAllterm();
	}


	
}
