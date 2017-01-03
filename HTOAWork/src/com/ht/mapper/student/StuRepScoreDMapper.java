package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StuRepScoreD;
public interface StuRepScoreDMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(StuRepScoreD record);

    StuRepScoreD selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuRepScoreD record);

    int delByUpdate(Integer id);
    
    List<StuRepScoreD> selectAll();
    
    List<StuRepScoreD> selectBysrsId(Integer id);
    
	int countBysrmdId(int srmdId);
}