package com.ht.service.finance;

import java.util.List;

import com.ht.popj.finance.Tuitionsetdetail;

public interface TuitionsetdetailService {

	int insert(Tuitionsetdetail record);

    int insertSelective(Tuitionsetdetail record);

    Tuitionsetdetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tuitionsetdetail record);

    int updateByPrimaryKey(Tuitionsetdetail record);
    
    int updateByTuitionKeySelective(Tuitionsetdetail record);
    
	List<Tuitionsetdetail> selectDynamic(Tuitionsetdetail record);
	
	List<Tuitionsetdetail> selectAllDetail(Tuitionsetdetail record);
}
