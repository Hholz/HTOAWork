package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FlowModelType;

public interface FlowModelTypeService {
	
	public int add(FlowModelType flowmodeltype);
	
	public void update(FlowModelType flowmodeltype);
	
	public void delete(int id);
	
	public List<FlowModelType> findList(FlowModelType flowmodeltype);
	
	public FlowModelType findById(int id);
	
	public List<FlowModelType> findInfo(int id);
}
