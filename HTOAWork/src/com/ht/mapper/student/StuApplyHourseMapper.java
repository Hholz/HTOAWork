package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StuApplyHourse;

public interface StuApplyHourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StuApplyHourse record);

    int insertSelective(StuApplyHourse record);

    StuApplyHourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuApplyHourse record);

    int updateByPrimaryKey(StuApplyHourse record);
    
    List<StuApplyHourse> selectStuApplyHourseAll();
    //¶¯Ì¬²éÑ¯
    List<StuApplyHourse> selectByDynamic(StuApplyHourse record);
}