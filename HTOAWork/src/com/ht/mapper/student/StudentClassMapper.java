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
    
    List<StudentClass> selectStudentclass(StudentClass cls);//�����а�
    List<StudentClass> selectNormalCls(StudentClass cls);//������������
    List<StudentClass> selectTestCls(StudentClass cls);//��������ѧ��
    
    List<StudentClass> selectStudentclass2(int id);
    
    List<StudentClass> selectallstduentclass();
    //ͨ��id��༶�������ð༶��stuList
    StudentClass selectById(Integer id);
    
    List<StudentClass> selectByLevelId(String id);

	int countById(Integer id);//��ѯ�ð��ж�����
	//��ѯ��ǰ�����εķǱ�ҵ�༶
	List<StudentClass> selectOnByClteac(String empId);
}