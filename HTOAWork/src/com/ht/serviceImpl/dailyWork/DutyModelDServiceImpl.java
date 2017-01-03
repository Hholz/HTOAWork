package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.DutymodelMapper;
import com.ht.mapper.dailyWork.DutymodeldetailMapper;
import com.ht.popj.dailyWork.DutyMaxTemp;
import com.ht.popj.dailyWork.Dutymodeldetail;
import com.ht.service.dailyWork.DutyModelDService;

public class DutyModelDServiceImpl implements DutyModelDService{

	@Autowired
	DutymodeldetailMapper dmdMapper;
	public int deleteById(Integer id) {
		return dmdMapper.deleteByPrimaryKey(id);
	}
	public int insertByPJ(Dutymodeldetail dmd) {
		return dmdMapper.insertSelective(dmd);
	}

	public Dutymodeldetail selectById(Integer id) {
		return dmdMapper.selectByPrimaryKey(id);
	}

	public int updateByPJ(Dutymodeldetail dmd) {
		return dmdMapper.updateByPrimaryKeySelective(dmd);
	}

	public List<Dutymodeldetail> selectAll() {
		return dmdMapper.selectAll();
	}

	@Override
	public List<Dutymodeldetail> selectByDutymodelId(Integer modelid) {
		return dmdMapper.selectByDutymodelId(modelid);
	}
	
	public List<DutyMaxTemp> selectDutyMaxBymodelId(Integer modelid) {
		return dmdMapper.selectDutyMaxBymodelId(modelid);
	}
	public List<String> selectWeksBymodelId(Integer modelid) {
		return dmdMapper.selectWeksBymodelId(modelid);
	}
	@Override
	public List<Dutymodeldetail> selectByPJ(Dutymodeldetail temp) {
		return dmdMapper.selectByPJ(temp);
	}

}
