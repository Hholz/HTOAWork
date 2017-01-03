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
    //ͨ���÷ֱ�id������ϸ
    List<StuRepScoreD> selectBysrsId(Integer id);
    //�������ø���ϸ���ж��ٸ�
	int countBysrmdId(Integer srmdId);
}