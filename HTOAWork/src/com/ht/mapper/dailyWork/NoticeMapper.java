package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Notice;

public interface NoticeMapper {
    //������Ϣ�б�
	List<Notice> findNoticeList1(Notice notice);
	List<Notice> findNoticeList2();
	//����������Ϣ
	int addNotice(Notice notice);
	//�޸Ĺ�����Ϣ
	int updateNotice(Notice notice);
	//ɾ��������Ϣ
	int deleteNoticeById(Integer id);
	//�����淢��ʱ�併�����򲢲�ѯǰ3����¼ 
	List<Notice> selectBynoticetime();
	//ͨ��id��ѯ��Ϣ
	Notice selectById(Integer id);
}