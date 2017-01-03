package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.StuStatus;

public interface StuStatusService {

	int deleteById(Integer id);

    int insertByPJ(StuStatus stuStatus);

    StuStatus selectById(Integer id);

    int updateByPJ(StuStatus stuStatus);

    int delByUpdate(Integer id);
    
    List<StuStatus> selectAll();
}
