package com.ht.mapper.flow;

import java.util.List;

import com.ht.popj.flow.FlowBaoxiaodetail;
import com.ht.popj.flow.FlowReturnmaterialdetail;

public interface FlowBaoxiaodetailMapper {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(FlowBaoxiaodetail record);

	FlowBaoxiaodetail selectByPrimaryKey(Integer id);

	List<FlowBaoxiaodetail> selectAll();

	List<FlowBaoxiaodetail> selectSelective(FlowBaoxiaodetail record);

	int updateByPrimaryKeySelective(FlowBaoxiaodetail record);
	
	List<FlowBaoxiaodetail> selectlength(Integer baoxiaoid);
	
    List<FlowBaoxiaodetail> selectBaoxiaodetaillist(String empid);

}