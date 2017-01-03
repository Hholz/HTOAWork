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
    //ͨ��ѧ����������
    List<StudentAttence> selectBystuId(Integer stuId);
    //��ѯһ���������
    List<StudentAttence> selectByclsId(Integer clsId);
    //��̬��ѯ
    List<StudentAttence> selectAllByPJ(StudentAttence attence);
    //�鿴����ð��Ƿ������ɿ���(Integer clsId, String createTime)
  	int isExistTheDate(Map map);
  	//����ѧ�ţ��������ٵ���������     ,ͳ�Ƹ���ĳ������ж��ٴ�
  	//(Integer stuId,String state,String createTime) 1,'����',2016-12
  	int sumByStuIdTime(Map map);
    ////һ��ѧ��һ���µĿ�����ϸ(Integer stuId, String createTime)
	List<StudentAttence> selectMonthBystuId(Map map);
}