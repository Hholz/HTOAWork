package com.ht.mapper.student;

import com.ht.popj.student.Gradstat;

public interface GradstatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Gradstat record);

    int insertSelective(Gradstat record);

    Gradstat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gradstat record);

    int updateByPrimaryKey(Gradstat record);
}