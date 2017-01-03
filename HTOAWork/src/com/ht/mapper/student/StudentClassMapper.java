package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentClass;

public interface StudentClassMapper {
    int deleteByPrimaryKey(Integer id);
    
    List<StudentClass> selectClassList(int fallid);
    
    List<StudentClass> selectClassListByFallid(int fallid);
    
    List<StudentClass> selectClassByComputer(int fallid);
    
    List<StudentClass> selectClassListByTercher(StudentClass studentClass);

    int insert(StudentClass record);

    int insertSelective(StudentClass record);

    StudentClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentClass record);

    int updateByPrimaryKey(StudentClass record);
    
    List<StudentClass> selectStudentclass(StudentClass cls);//查所有班
    List<StudentClass> selectNormalCls(StudentClass cls);//查所有正常班
    List<StudentClass> selectTestCls(StudentClass cls);//查所有试学班
    
    List<StudentClass> selectStudentclass2(int id);
    
    List<StudentClass> selectallstduentclass();
    //通过id查班级并包含该班级的stuList
    StudentClass selectById(Integer id);
    
    List<StudentClass> selectByLevelId(String id);

	int countById(Integer id);//查询该班有多少人
	//查询当前班主任的非毕业班级
	List<StudentClass> selectOnByClteac(String empId);
}