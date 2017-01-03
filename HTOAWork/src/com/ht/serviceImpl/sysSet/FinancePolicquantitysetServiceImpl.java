package com.ht.serviceImpl.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.FinancePolicquantitysetMapper;
import com.ht.popj.sysSet.FinancePolicquantityset;
import com.ht.service.sysSet.FinancePolicquantitysetService;

public class FinancePolicquantitysetServiceImpl implements FinancePolicquantitysetService {
	@Autowired
	FinancePolicquantitysetMapper financepolicquantitysetmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		financepolicquantitysetmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FinancePolicquantityset record) {
//		int a = 1;
//		try {
//			financepolicquantitysetmapper.insertSelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		if (a == 0) {
//			return a;
//		}
//		return record.getId();
		return financepolicquantitysetmapper.insertSelective(record);
	}

	@Override
	public FinancePolicquantityset selectByPrimaryKey(Integer id) {
		return financepolicquantitysetmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FinancePolicquantityset> selectAll() {
		return financepolicquantitysetmapper.selectAll();
	}

	@Override
	public List<FinancePolicquantityset> selectSelective(FinancePolicquantityset record) {
		return financepolicquantitysetmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FinancePolicquantityset record) {
		int a = 1;
		try {
			financepolicquantitysetmapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			a = 0;
			// record.toString();//日志输出
		}
		return a;
	}

	@Override
	public FinancePolicquantityset selectByMaterialid(Integer id) {
		return financepolicquantitysetmapper.selectByMaterialid(id);
	}

}
