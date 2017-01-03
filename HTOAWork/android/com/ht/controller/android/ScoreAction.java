package com.ht.controller.android;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.android.util.ResultMessage;
import com.ht.pojo.AScore;
import com.ht.pojo.AScoreD;
import com.ht.popj.student.StuRepScore;
import com.ht.popj.student.StuRepScoreD;
import com.ht.service.student.StuRepScoreDService;
import com.ht.service.student.StuRepScoreService;

@Controller
@RequestMapping("android/score")
public class ScoreAction {

	@Autowired
	StuRepScoreService srsSercice;
	@Autowired
	StuRepScoreDService srsdSercice;
	@RequestMapping("/getSrs")
	public @ResponseBody ResultMessage GetSrs(Integer stuId){
		List<StuRepScore> sList = new ArrayList<StuRepScore>();
		sList = srsSercice.selectByStuId(stuId);
		
		List<AScore> asList = new ArrayList<AScore>();
		for(StuRepScore s : sList){
			AScore as = new AScore();
			as.setId(s.getId());
			as.setAllScore(s.getAllScore());
			as.setSrmId(s.getSrmId());
			as.setSrsScore(s.getSrsScore());
			as.setSrsDate(s.getSrsDate());
			as.setStuId(s.getStuId());
			as.setTeacName(s.getTeacName());
			as.setRepName(s.getStuReplyModel().getSrmName());
			asList.add(as);
		}
		ResultMessage rm = new ResultMessage();
		rm.setResultCode("200");
		rm.setResultMessage("成功！");
		rm.setContext(asList);
		return rm;
	}
	@RequestMapping("/getSrsd")
	public @ResponseBody ResultMessage GetSrsd(Integer srsId){
		List<StuRepScoreD> list = new ArrayList<StuRepScoreD>();
		list = srsdSercice.selectBysrsId(srsId);
		
		List<AScoreD> asdList = new ArrayList<AScoreD>();
		for(StuRepScoreD s : list){
			AScoreD asd = new AScoreD();
			asd.setId(s.getId());
			asd.setSrmdName(s.getStuReplyModelD().getSrmdName());
			asd.setSrsdScore(s.getSrsdScore());
			asd.setAllScore(s.getStuReplyModelD().getSrmdScore());
			asdList.add(asd);
		}
		ResultMessage rm = new ResultMessage();
		rm.setResultCode("200");
		rm.setResultMessage("成功！");
		rm.setContext(asdList);
		return rm;
	}
}
