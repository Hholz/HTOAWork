package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ht.mapper.education.EduFeedbackStartMapper;
import com.ht.popj.education.EduFeedbackStart;
import com.ht.service.education.FeedBackStartService;

/*@Repository*/
public class FeedBackStartServiceImpl implements FeedBackStartService {

	@Autowired
	EduFeedbackStartMapper mapper;

	@Override
	public int addFeedBackStart(EduFeedbackStart record) {
		return mapper.addFeedBackStart(record);
	}

	@Override
	public int deleteFeedBackStart(int id) {
		return mapper.deleteFeedBackStart(id);
	}

	@Override
	public EduFeedbackStart getFeedBackStart(int id) {
		return mapper.getFeedBackStart(id);
	}

	@Override
	public List<EduFeedbackStart> getAllFeedBackStart() {
		return mapper.getAllFeedBackStart();
	}

	@Override
	public List<EduFeedbackStart> getSomeFeedBackStart(EduFeedbackStart record) {
		return mapper.getSomeFeedBackStart(record);
	}

	@Override
	public int updateFeedBackStart(EduFeedbackStart record) {
		return mapper.updateFeedBackStart(record);
	}

}
