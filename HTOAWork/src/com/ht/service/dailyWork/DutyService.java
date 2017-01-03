package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Duty;
import com.ht.popj.dailyWork.Emp;

public interface DutyService {
	//ֵ���б�
	List<Duty> findDutyList1(Duty duty);
	List<Duty> findDutyList2();
	//����ֵ��
	int addDuty(Duty duty);
	//�޸�ֵ��
	int updateDuty(Duty duty);
	//ɾ��ֵ��
	int deleteDuty(Integer id);
	//ͨ��id��ѯ��Ϣ
	Duty selectById(Integer id);
	//��ѯ�����е�����
	List<String> selectweeks(String dutyid);
	List<String> selectweeks2();
	//��ѯ��������Ӧ���ܼ�
	List<String> selectweekends(String weeks,String dutyid);
	List<String> selectweekends2(String weeks);
	//��ѯ��������Ӧ���ܼ������Ӧ��ֵ����
	List<Emp> selectdutyid(String weeks,String weekends);
}
