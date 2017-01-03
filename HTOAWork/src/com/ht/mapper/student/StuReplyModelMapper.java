package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StuReplyModel;

public interface StuReplyModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StuReplyModel record);

    StuReplyModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuReplyModel record);

    int delByUpdate(Integer id);
    
    List<StuReplyModel> selectAll();
    
    List<StuReplyModel> selectAllUnRep();
    
    List<StuReplyModel> selectByPJ(StuReplyModel srm);
}