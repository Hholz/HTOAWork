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
    
    //��������status = 0����ʾɾ��
    int upStaById(Integer id);
    //map���List<Integer> ids , Integer classid;������ѧ����classid���ﵽ�ְ��Ŀ��
    int upStuClsByIds(Map map);
   //map���List<Integer> ids , Integer huorid;������ѧ����huorid���ﵽ�������Ŀ��
    int upStuHuorByIds(Map map);
    //��ѯ����δ�ְ��
    List<Student> selectByPJnoCls(Student record);
  //��ѯ���з��˰��
    List<Student>selectByPJhasCls(Student record);
    
    int countstudentseId(Integer id);
    //map���clsIds,��ѯ��Щ�༶��ѧ��
    List<Student> selectByClsIds(Map map);
    
    String findBigCode(String classno);
    //ͨ��stunoѧ���������ѧ��
	Student findStuByNo(String stuno);
	
	List<Student> selectByClsIdsRandom(Map map);
	//ͨ���༶id��ѧ��list
	List<Student> selectByclsId(Integer clsId);
	//ͨ��id�޸�ѧ���ڶ�״̬
	int updateStuStatusById(String id);
}