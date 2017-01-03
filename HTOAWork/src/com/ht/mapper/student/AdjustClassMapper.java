package com.ht.mapper.student;

import java.util.List;
import java.util.Map;

import com.ht.popj.student.AdjustClass;

public interface AdjustClassMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(AdjustClass record);

    AdjustClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdjustClass record);

    List<AdjustClass> selectbyStuId(Integer stuId);
    //map里放stuId，查询该班主任所有学生的请假单
    List<AdjustClass> selectbyStuIds(Map map);
    
    //通过要原班级的ids来取，所有
	List<AdjustClass> selectAllbyClsId(Map map);
    //通过原班级的ids来取 ，未处理的
    List<AdjustClass> selectbyClsIdsNodel(Map map);
    
    
    //通过要分到的班级的ids来取
	List<AdjustClass> selectbyToClsIdsNodel(Map map);
}