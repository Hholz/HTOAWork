package com.ht.mapper.student;

import java.util.List;
import java.util.Map;

import com.ht.popj.student.StudentAttence;

public interface StudentAttenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StudentAttence record);

    StudentAttence selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentAttence record);

    List<StudentAttence> selectAll();
    //通过学生来查所有
    List<StudentAttence> selectBystuId(Integer stuId);
    //查询一个班的所有
    List<StudentAttence> selectByclsId(Integer clsId);
    //动态查询
    List<StudentAttence> selectAllByPJ(StudentAttence attence);
    //查看该天该班是否已生成考勤(Integer clsId, String createTime)
  	int isExistTheDate(Map map);
  	//传入学号，正常（迟到），哪月     ,统计该月某种情况有多少次
  	//(Integer stuId,String state,String createTime) 1,'正常',2016-12
  	int sumByStuIdTime(Map map);
    ////一个学生一个月的考勤详细(Integer stuId, String createTime)
	List<StudentAttence> selectMonthBystuId(Map map);
}