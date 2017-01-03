package com.ht.service.market;

import java.util.List;

import com.ht.popj.market.IntentionStudent;

public interface IntentionStudentService {
	//����ѧ���б�
	List<IntentionStudent> findIntentionStudentList1(IntentionStudent intenstu);
	List<IntentionStudent> findIntentionStudentList2();
	List<IntentionStudent> findIntentionStudentList3();
	//��������ѧ��
	int addIntentionStudent(IntentionStudent intenstu);
	//�޸�����ѧ����Ϣ
	int updateIntentionStudent(IntentionStudent intenstu);
	//ɾ������ѧ������״̬��Ϊ0��
	int deleteIntentionStudentById(Integer id);
	//ɾ������ѧ��������ɾ����
	int deleteIntentionStudent(Integer id);
	//ͨ��id������Ϣ
	IntentionStudent selectByPrimaryKey(Integer id);
}
