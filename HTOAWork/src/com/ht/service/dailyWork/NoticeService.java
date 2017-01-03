package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Notice;
import com.ht.popj.dailyWork.Noticetype;

public interface NoticeService {
	//��������б�
	List<Noticetype> findNoticeTypeList2();
	//�����������
	int addNoticeType(Noticetype noticeType);
	//�޸Ĺ������
	int updateNoticeType(Noticetype noticeType);
	//ɾ���������
	int deleteNoticeTypeById(Integer id);
	
	//�����淢��ʱ�併�����򲢲�ѯǰ3����¼ 
	List<Notice> selectBynoticetime();
	//ͨ��id��ѯ��Ϣ
	Notice selectById(Integer id);
	
	//������Ϣ�б�
	List<Notice> findNoticeList1(Notice notice);
	List<Notice> findNoticeList2();
	//����������Ϣ
	int addNotice(Notice notice);
	//�޸Ĺ�����Ϣ
	int updateNotice(Notice notice);
	//ɾ��������Ϣ
	int deleteNoticeById(Integer id);
}
