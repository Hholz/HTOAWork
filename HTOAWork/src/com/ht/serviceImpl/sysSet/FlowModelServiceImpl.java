package com.ht.serviceImpl.sysSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.sysSet.FlowModelMapper;
import com.ht.popj.sysSet.FlowModel;
import com.ht.service.sysSet.FlowModelService;

public class FlowModelServiceImpl implements FlowModelService {

	@Autowired
	FlowModelMapper fmm;

	@Override
	public int add(FlowModel flowmodel) {
		fmm.insertSelective(flowmodel);
		return flowmodel.getId();
	}

	@Override
	public void update(FlowModel flowmodel) {
		fmm.updateByPrimaryKeySelective(flowmodel);

	}

	@Override
	public void delete(int id) {
		fmm.deleteByPrimaryKey(id);
	}

	@Override
	public List<FlowModel> findList(FlowModel flowmodeltype) {
		List<FlowModel> fmodList = new ArrayList<FlowModel>();
		fmodList = fmm.selectByName(flowmodeltype);
		return fmodList;
	}

	@Override
	public FlowModel findById(int id) {
		FlowModel f = fmm.selectByPrimaryKey(id);
		return f;
	}

}
