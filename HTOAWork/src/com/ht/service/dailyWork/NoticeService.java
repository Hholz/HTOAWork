package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Notice;
import com.ht.popj.dailyWork.Noticetype;

public interface NoticeService {
	//公告类别列表
	List<Noticetype> findNoticeTypeList2();
	//新增公告类别
	int addNoticeType(Noticetype noticeType);
	//修改公告类别
	int updateNoticeType(Noticetype noticeType);
	//删除公告类别
	int deleteNoticeTypeById(Integer id);
	
	//按公告发布时间降序排序并查询前3条记录 
	List<Notice> selectBynoticetime();
	//通过id查询信息
	Notice selectById(Integer id);
	
	//公告信息列表
	List<Notice> findNoticeList1(Notice notice);
	List<Notice> findNoticeList2();
	//新增公告信息
	int addNotice(Notice notice);
	//修改公告信息
	int updateNotice(Notice notice);
	//删除公告信息
	int deleteNoticeById(Integer id);
}
