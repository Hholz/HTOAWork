package com.ht.service.student;

import java.util.List;
import java.util.Map;

import com.ht.annotation.SystemServiceLog;
import com.ht.popj.student.Student;

public interface StudentService {

	int deleteById(Integer id);

    int addStudent(Student student);

    Student selectById(Integer id);

    int updateStuById(Student student);
    
    List<Student> selectStudentAll();
    @SystemServiceLog(description = "Service==查询用户list") 
    List<Student> selectByDynamic(Student student);
    
    int upStaById(Integer id);

  //map里放List<int> ids , int classid;来更新学生的classid，达到分班的目的
    int upStuClsByIds(Map map);
   //map里放List<int> ids , int huorid;来更新学生的huorid，达到分宿舍的目的
    int upStuHuorByIds(Map map);
  //查询所有未分班的
    List<Student> selectByPJnoCls(Student student);
  //查询所有了分班的
    List<Student>selectByPJhasCls(Student record);
    //学生个数
    int countstudentseId(Integer id);
    
    //传入班级编号，找出当前最大的学生编号
    int findBigCode(String classno);
    
  //map里放clsIds,查询这些班级的学生
    List<Student> selectByClsIds(Map map);

	Student findStuByNo(String stuno);
	
	List<Student> selectByClsIdsRandom(Map map);
	//通过班级id查学生list
	List<Student> selectByclsId(Integer clsId);
	//通过id修改查学生在读状态
	int updateStuStatusById(String id);
}
