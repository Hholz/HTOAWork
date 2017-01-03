package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.Tuitionset;

public interface TuitionsetService {

	int insert(Tuitionset record);

    int insertSelective(Tuitionset record);
    
    int updateByPrimaryKeySelective(Tuitionset record);

    int updateByPrimaryKey(Tuitionset record);
    
	List<Tuitionset> selectByDynamic(Tuitionset record);
	
	int selCountByTermId(Integer termId);
	
	List<Tuitionset> selectByDynamicStatus(Tuitionset record);
	
	Tuitionset selectByOnlyTuition(Tuitionset record);
}
