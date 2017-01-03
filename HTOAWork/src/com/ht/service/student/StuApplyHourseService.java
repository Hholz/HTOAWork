package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StuApplyHourse;

public interface StuApplyHourseService {

	List<StuApplyHourse> selectStuApplyHourseAll();
    //¶¯Ì¬²éÑ¯
    List<StuApplyHourse> selectByDynamic(StuApplyHourse record);
    
    int insert(StuApplyHourse record);
    int deleteByPrimaryKey(Integer id);
    int insertSelective(StuApplyHourse record);
    int updateByPrimaryKeySelective(StuApplyHourse record);
}
