package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StuRepScore;


public interface StuRepScoreService {

	int deleteById(Integer id);

    int insertByPJ(StuRepScore srs);

    StuRepScore selectById(Integer id);

    int updateByPJ(StuRepScore srs);
    
    int delByUpdate(Integer id);
    
    List<StuRepScore> selectAll();
    
    List<StuRepScore> selectByStuId(Integer stuId);

	int countBysrmId(Integer srmId);
	//mbName  clsId  empName
	List<StuRepScore> selectByPJ(StuRepScore srs,String mbName,int clsId,String empName);
}

