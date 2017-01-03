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
    //ͨ��ѧ����������
    List<StudentAttence> selectBystuId(Integer stuId);
    //��ѯһ���������
    List<StudentAttence> selectByclsId(Integer clsId);
    //��̬��ѯ
    List<StudentAttence> selectAllByPJ(StudentAttence attence);

	int createOneDayAttence(List<Student> stuList, String createTime);
	//�鿴����ð��Ƿ������ɿ���
	boolean isExistTheDate(Integer clsId, String createTime);
	////����ѧ�ţ��������ٵ���������     ,ͳ�Ƹ���ĳ������ж��ٴ�
  	int sumByStuIdTime(Integer stuId,String state,String createTime);
  	//��ѯͳ��һ���࣬һ���µĿ���ͳ��
  	List<StuAttenceCount> CountAttenByclsId(Integer clsId,String createTime);
  	//һ��ѧ��һ���µĿ�����ϸ
  	List<StudentAttence> selectMonthBystuId(Integer stuId,String createTime);
}
