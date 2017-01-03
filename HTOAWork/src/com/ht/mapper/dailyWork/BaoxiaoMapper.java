package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Baoxiao;
import com.ht.popj.dailyWork.Emp;

public interface BaoxiaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Baoxiao record);

    Baoxiao selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Baoxiao record);
    
    List<Baoxiao> selectList(Baoxiao record);
    
    List<Baoxiao> selectBaoxiaoTask(Emp id);
    
    List<Baoxiao> selectBaoxiaoOvertask(Emp id);
}