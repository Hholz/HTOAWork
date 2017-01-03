package com.ht.serviceImpl.finance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.finance.SalaryDetailMapper;
import com.ht.popj.finance.SalaryDetail;
import com.ht.service.finance.SalaryDetailService;

public class SalaryDetailServiceImpl implements SalaryDetailService{

	@Autowired
	SalaryDetailMapper detailMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return detailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SalaryDetail record) {
		// TODO Auto-generated method stub
		return detailMapper.insert(record);
	}

	@Override
	public int insertSelective(SalaryDetail record) {
		// TODO Auto-generated method stub
		return detailMapper.insertSelective(record);
	}

	@Override
	public SalaryDetail selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return detailMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SalaryDetail record) {
		// TODO Auto-generated method stub
		return detailMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SalaryDetail record) {
		// TODO Auto-generated method stub
		return detailMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SalaryDetail> selectByDynamic(SalaryDetail record) {
		// TODO Auto-generated method stub
		return detailMapper.selectByDynamic(record);
	}

	@Override
	public int deleteBySalaryId(Integer salaryid) {
		return detailMapper.deleteBySalaryId(salaryid);
	}

	@Override
	public int insertMuch(ArrayList<SalaryDetail> salArr) {
		return detailMapper.insertMuch(salArr);
	}
	
	
}
