package com.ht.service.student;

import java.util.List;
import java.util.Map;

import com.ht.popj.student.StuAttenceCount;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentAttence;

public interface StudentAttenceService {

	int deleteById(Integer id);

    int insertByPJ(StudentAttence attence);

    StudentAttence selectById(Integer id);

    int updateByPJ(StudentAttence attence);
    
    List<StudentAttence> selectAll();
    //通过学生来查所有
    List<StudentAttence> selectBystuId(Integer stuId);
    //查询一个班的所有
    List<StudentAttence> selectByclsId(Integer clsId);
    //动态查询
    List<StudentAttence> selectAllByPJ(StudentAttence attence);

	int createOneDayAttence(List<Student> stuList, String createTime);
	//查看该天该班是否已生成考勤
	boolean isExistTheDate(Integer clsId, String createTime);
	////传入学号，正常（迟到），哪月     ,统计该月某种情况有多少次
  	int sumByStuIdTime(Integer stuId,String state,String createTime);
  	//查询统计一个班，一个月的考勤统计
  	List<StuAttenceCount> CountAttenByclsId(Integer clsId,String createTime);
  	//一个学生一个月的考勤详细
  	List<StudentAttence> selectMonthBystuId(Integer stuId,String createTime);
}
