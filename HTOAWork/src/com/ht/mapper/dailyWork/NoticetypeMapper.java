package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Noticetype;

public interface NoticetypeMapper {
    //公告类别列表
	List<Noticetype> findNoticeTypeList2();
	//新增公告类别
	int addNoticeType(Noticetype noticeType);
	//修改公告类别
	int updateNoticeType(Noticetype noticeType);
	//删除公告类别
	int deleteNoticeTypeById(Integer id);
}