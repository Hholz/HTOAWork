package com.ht.mapper.education;

import com.ht.popj.education.EduCourseTeacher;
import com.ht.popj.education.EduCourseTeacherExample;
import java.util.List;

public interface EduCourseTeacherMapper {
    int countByExample(EduCourseTeacherExample example);

    int deleteByExample(EduCourseTeacherExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EduCourseTeacher record);

    int insertSelective(EduCourseTeacher record);

    List<EduCourseTeacher> selectByExample(EduCourseTeacherExample example);

    EduCourseTeacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduCourseTeacher record);

    int updateByPrimaryKey(EduCourseTeacher record);
    
 	List<EduCourseTeacher> selectTeacherAll();

  	List<EduCourseTeacher> selectByDynamic(EduCourseTeacher course);

  	int getCount();
}