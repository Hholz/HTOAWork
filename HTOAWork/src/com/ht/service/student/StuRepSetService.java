package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StuRepSet;

public interface StuRepSetService {
	List<StuRepSet> selectStuRepSet(StuRepSet sturepset);
    int insertStuRepSet(StuRepSet sturepset);
    int updateStuRepSet(StuRepSet sturepset);
    int deleteStuRepSet(Integer id);
}
