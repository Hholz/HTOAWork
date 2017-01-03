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
    @SystemServiceLog(description = "Service==��ѯ�û�list") 
    List<Student> selectByDynamic(Student student);
    
    int upStaById(Integer id);

  //map���List<int> ids , int classid;������ѧ����classid���ﵽ�ְ��Ŀ��
    int upStuClsByIds(Map map);
   //map���List<int> ids , int huorid;������ѧ����huorid���ﵽ�������Ŀ��
    int upStuHuorByIds(Map map);
  //��ѯ����δ�ְ��
    List<Student> selectByPJnoCls(Student student);
  //��ѯ�����˷ְ��
    List<Student>selectByPJhasCls(Student record);
    //ѧ������
    int countstudentseId(Integer id);
    
    //����༶��ţ��ҳ���ǰ����ѧ�����
    int findBigCode(String classno);
    
  //map���clsIds,��ѯ��Щ�༶��ѧ��
    List<Student> selectByClsIds(Map map);

	Student findStuByNo(String stuno);
	
	List<Student> selectByClsIdsRandom(Map map);
	//ͨ���༶id��ѧ��list
	List<Student> selectByclsId(Integer clsId);
	//ͨ��id�޸Ĳ�ѧ���ڶ�״̬
	int updateStuStatusById(String id);
}
