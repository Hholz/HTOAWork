package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Noticetype;

public interface NoticetypeMapper {
    //��������б�
	List<Noticetype> findNoticeTypeList2();
	//�����������
	int addNoticeType(Noticetype noticeType);
	//�޸Ĺ������
	int updateNoticeType(Noticetype noticeType);
	//ɾ���������
	int deleteNoticeTypeById(Integer id);
}