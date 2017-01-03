package com.ht.mapper.education;

import com.ht.popj.education.EduCourse;
import com.ht.popj.education.EduCourseExample;
import com.ht.popj.education.EduDept;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EduCourseMapper {
    int countByExample(EduCourseExample example);

    int deleteByExample(EduCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EduCourse record);

    int insertSelective(EduCourse record);

    List<EduCourse> selectByExample(EduCourseExample example);

    EduCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EduCourse record, @Param("example") EduCourseExample example);

    int updateByExample(@Param("record") EduCourse record, @Param("example") EduCourseExample example);

    int updateByPrimaryKeySelective(EduCourse record);

    int updateByPrimaryKey(EduCourse record);
    
  	List<EduCourse> selectCourseAll();

  	List<EduCourse> selectByDynamic(EduCourse course);

  	int getCount(Integer id);
}