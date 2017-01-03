package com.ht.service.education;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ht.popj.education.EduCourseType;
import com.ht.popj.education.EduCourseTypeExample;

public interface CourseTypeService {

	int countByExample(EduCourseTypeExample example);

	int deleteByExample(EduCourseTypeExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(EduCourseType record);

	int insertSelective(EduCourseType record);

	List<EduCourseType> selectByExample(EduCourseTypeExample example);

	EduCourseType selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") EduCourseType record, @Param("example") EduCourseTypeExample example);

	int updateByExample(@Param("record") EduCourseType record, @Param("example") EduCourseTypeExample example);

	int updateByPrimaryKeySelective(EduCourseType record);

	int updateByPrimaryKey(EduCourseType record);

	List<EduCourseType> selectCourseTypeAll();

	List<EduCourseType> selectByDynamic(EduCourseType type);

}
