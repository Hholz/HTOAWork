package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StuRepSet;

public interface StuRepSetMapper {
    List<StuRepSet> selectStuRepSet(StuRepSet sturepset);
    int insertStuRepSet(StuRepSet sturepset);
    int updateStuRepSet(StuRepSet sturepset);
    int deleteStuRepSet(Integer id);
}