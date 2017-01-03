package com.ht.serviceImpl.sysSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.FlowModelTypeMapper;
import com.ht.popj.sysSet.FlowModelType;
import com.ht.service.sysSet.FlowModelTypeService;

public class FlowModelTypeServiceImpl implements FlowModelTypeService{

	@Autowired
	FlowModelTypeMapper fdtMapper;
	
	@Override
	public int add(FlowModelType flowmodeltype) {
		fdtMapper.insertSelective(flowmodeltype);
		return flowmodeltype.getId();
	}

	@Override
	public void update(FlowModelType flowmodeltype) {
		fdtMapper.updateByPrimaryKeySelective(flowmodeltype);
	}

	@Override
	public void delete(int id) {
		fdtMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<FlowModelType> findList(FlowModelType flowmodeltype) {
		List<FlowModelType> fmodtyList = fdtMapper.selectByName(flowmodeltype);
		return fmodtyList;
	}

	@Override
	public FlowModelType findById(int id) {
		FlowModelType ft = fdtMapper.selectByPrimaryKey(id);
		return ft;
	}

	@Override
	public List<FlowModelType> findInfo(int id) {
		
		return fdtMapper.findInfo(id);
	}

}
