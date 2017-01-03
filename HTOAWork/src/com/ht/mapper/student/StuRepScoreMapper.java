package com.ht.mapper.student;

import java.util.List;
import java.util.Map;

import com.ht.popj.student.StuRepScore;

public interface StuRepScoreMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(StuRepScore record);

    StuRepScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuRepScore record);

    int delByUpdate(Integer id);
    
    List<StuRepScore> selectAll();

	List<StuRepScore> selectByStuId(Integer stuId);
	//查该模板有多少条数据
	int countBysrmId(Integer id);
	//srs  mbName  clsId  empName
	List<StuRepScore> selectByPJ(Map map);
}