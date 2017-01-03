package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.Residence;

public interface ResidenceService {
	int deleteById(Integer id);

    int insertByPJ(Residence residence);

    Residence selectById(Integer id);

    int updateByPJ(Residence residence);
    
    int delByUpdate(Integer id);
    
    List<Residence> selectAll();
    
}
