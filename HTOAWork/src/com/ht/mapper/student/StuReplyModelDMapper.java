package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StuReplyModelD;

public interface StuReplyModelDMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StuReplyModelD record);

    StuReplyModelD selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuReplyModelD record);

    int delByUpdate(Integer id);
    
    List<StuReplyModelD> selectAll();
    
    List<StuReplyModelD> selectByPJ(StuReplyModelD srmd);
    
    List<StuReplyModelD> selectBysrmId(Integer srmId);
}