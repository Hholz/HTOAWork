package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Baoxiaotype;

public interface BaoxiaotypeService {
	int deleteByPrimaryKey(Integer id);

    int insert(Baoxiaotype record);

    Baoxiaotype selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Baoxiaotype record);
    
    List<Baoxiaotype> Baoxiaotypelist(Baoxiaotype record);
}
