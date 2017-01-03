package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StuReplyModelD;

public interface StuReplyModelDService {

	int deleteById(Integer id);

    int insertByPJ(StuReplyModelD srmd);

    StuReplyModelD selectById(Integer id);

    int updateByPJ(StuReplyModelD srmd);
    
    int delByUpdate(Integer id);
    
    List<StuReplyModelD> selectAll();
    
    List<StuReplyModelD> selectByPJ(StuReplyModelD srmd);
    //ͨ��ģ�������ģ����ϸ��
    List<StuReplyModelD> selectBysrmId(Integer srmId);
}
