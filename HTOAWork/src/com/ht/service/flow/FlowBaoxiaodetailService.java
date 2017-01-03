package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.FlowBaoxiaodetail;

public interface FlowBaoxiaodetailService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(FlowBaoxiaodetail record);

	FlowBaoxiaodetail selectByPrimaryKey(Integer id);

	List<FlowBaoxiaodetail> selectAll();

	List<FlowBaoxiaodetail> selectSelective(FlowBaoxiaodetail record);

	int updateByPrimaryKeySelective(FlowBaoxiaodetail record);
	
	List<FlowBaoxiaodetail> selectlength(Integer baoxiaoid);
	
    List<FlowBaoxiaodetail> selectBaoxiaodetaillist(String empid);
}
