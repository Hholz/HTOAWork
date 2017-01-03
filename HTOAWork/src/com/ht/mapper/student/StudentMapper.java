package com.ht.mapper.student;

import java.util.List;
import java.util.Map;

import com.ht.popj.student.Student;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Student record);
    
    List<Student> selectStudentList(int classid);
    
    List<Student> selectStudListComputer(int classid);
    
    List<Student> selectStudListFeedBack(int classid);

    Student selectByPrimaryKey(Integer id);
    
    Student selectByPrimaryKey2(Integer id);

    int updateByPrimaryKeySelective(Student record);
    
    List<Student> selectStudentAll();
    int countByHourseId(int id);
    List<Student> selectByDynamic(Student record);
    
    //更改数据status = 0，表示删除
    int upStaById(Integer id);
    //map里放List<Integer> ids , Integer classid;来更新学生的classid，达到分班的目的
    int upStuClsByIds(Map map);
   //map里放List<Integer> ids , Integer huorid;来更新学生的huorid，达到分宿舍的目的
    int upStuHuorByIds(Map map);
    //查询所有未分班的
    List<Student> selectByPJnoCls(Student record);
  //查询所有分了班的
    List<Student>selectByPJhasCls(Student record);
    
    int countstudentseId(Integer id);
    //map里放clsIds,查询这些班级的学生
    List<Student> selectByClsIds(Map map);
    
    String findBigCode(String classno);
    //通过stuno学生编号来查学生
	Student findStuByNo(String stuno);
	
	List<Student> selectByClsIdsRandom(Map map);
	//通过班级id查学生list
	List<Student> selectByclsId(Integer clsId);
	//通过id修改学生在读状态
	int updateStuStatusById(String id);
}