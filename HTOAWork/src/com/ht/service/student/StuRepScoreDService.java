package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StuRepScoreD;

public interface StuRepScoreDService {

	int deleteById(Integer id);

    int insertByPJ(StuRepScoreD srsd);

    StuRepScoreD selectById(Integer id);

    int updateByPJ(StuRepScoreD srsd);
    
    int delByUpdate(Integer id);
    
    List<StuRepScoreD> selectAll();
    //通过得分表id来查明细
    List<StuRepScoreD> selectBysrsId(Integer id);
    //计数，用该明细的有多少个
	int countBysrmdId(Integer srmdId);
}