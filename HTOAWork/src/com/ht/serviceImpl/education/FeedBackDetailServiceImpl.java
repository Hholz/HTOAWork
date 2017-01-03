package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.education.EduFeedbackDetailMapper;
import com.ht.popj.education.EduFeedbackDetail;
import com.ht.service.education.FeedBackDetailService;

public class FeedBackDetailServiceImpl implements FeedBackDetailService {

	@Autowired
	EduFeedbackDetailMapper mapper;

	@Override
	public int deleteFeedbackDetail(Integer id) {
		return mapper.deleteFeedbackDetail(id);
	}

	@Override
	public int addFeedbackDetail(EduFeedbackDetail record) {
		return mapper.addFeedbackDetail(record);
	}

	@Override
	public EduFeedbackDetail getFeedbackDetail(Integer id) {
		return mapper.getFeedbackDetail(id);
	}

	@Override
	public int updateFeedbackDetail(EduFeedbackDetail record) {
		return mapper.updateFeedbackDetail(record);
	}

	@Override
	public List<EduFeedbackDetail> getAllFeedbackDetail(EduFeedbackDetail record) {
		return mapper.getAllFeedbackDetail(record);
	}

	@Override
	public List<EduFeedbackDetail> getSomeFeedBackDetail(EduFeedbackDetail record) {
		return mapper.getSomeFeedBackDetail(record);
	}

	@Override
	public List<Integer> getSumByFeedBackId(Integer id) {
		return mapper.getSumByFeedBackId(id);
	}

}
