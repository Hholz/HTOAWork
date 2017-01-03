package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.NoticeMapper;
import com.ht.mapper.dailyWork.NoticetypeMapper;
import com.ht.popj.dailyWork.Notice;
import com.ht.popj.dailyWork.Noticetype;
import com.ht.service.dailyWork.NoticeService;

public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private NoticetypeMapper noticeTypeMapper;
	
	//--------------公告信息-----------------
	@Override
	public List<Notice> findNoticeList1(Notice notice) {
		return noticeMapper.findNoticeList1(notice);
	}

	@Override
	public List<Notice> findNoticeList2() {
		return noticeMapper.findNoticeList2();
	}

	@Override
	public int addNotice(Notice notice) {
		return noticeMapper.addNotice(notice);
	}

	@Override
	public int updateNotice(Notice notice) {
		return noticeMapper.updateNotice(notice);
	}

	@Override
	public int deleteNoticeById(Integer id) {
		return noticeMapper.deleteNoticeById(id);
	}
	//---------------------公告类别--------------
	@Override
	public List<Noticetype> findNoticeTypeList2() {
		return noticeTypeMapper.findNoticeTypeList2();
	}

	@Override
	public int addNoticeType(Noticetype noticeType) {
		return noticeTypeMapper.addNoticeType(noticeType);
	}

	@Override
	public int updateNoticeType(Noticetype noticeType) {
		return noticeTypeMapper.updateNoticeType(noticeType);
	}

	@Override
	public int deleteNoticeTypeById(Integer id) {
		return noticeTypeMapper.deleteNoticeTypeById(id);
	}

	@Override
	public List<Notice> selectBynoticetime() {
		return noticeMapper.selectBynoticetime();
	}

	@Override
	public Notice selectById(Integer id) {
		return noticeMapper.selectById(id);
	}

}
