package com.ht.service.education;

import java.util.List;

import com.ht.popj.education.EduMajor;

public interface MajorService {

	int deleteByPrimaryKey(Integer id);

    int insert(EduMajor record);

    int insertSelective(EduMajor record);

    EduMajor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduMajor record);

    int updateByPrimaryKey(EduMajor record);
    
    List<EduMajor> selectMajorAll();
	  //��̬��ѯ
	List<EduMajor> selectByDynamic(EduMajor major);
	   
	int findInfoById(Integer id);
}
