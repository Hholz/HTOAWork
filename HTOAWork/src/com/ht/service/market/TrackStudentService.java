package com.ht.service.market;

import java.util.List;

import com.ht.popj.market.TrackStudent;

public interface TrackStudentService {
	//����ѧ���б�
	List<TrackStudent> findTrackStudentList1(TrackStudent trackstu);
	List<TrackStudent> findTrackStudentList2();
	//��������ѧ��
	int addTrackStudent(TrackStudent trackstu);
	//�޸ĸ���ѧ��
	int updateTrackStudent(TrackStudent trackstu);
	//ɾ������ѧ��
	int deleteTrackStudentById(Integer id);
	
	TrackStudent selectById(Integer id);
}
