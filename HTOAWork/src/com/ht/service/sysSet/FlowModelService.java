package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FlowModel;

public interface FlowModelService {
	
	public int add(FlowModel flowmodeltype);

	public void update(FlowModel flowmodeltype);

	public void delete(int id);

	public List<FlowModel> findList(FlowModel flowmodel);

	public FlowModel findById(int id);
}
