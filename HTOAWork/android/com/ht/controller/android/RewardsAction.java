package com.ht.controller.android;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.android.util.ResultMessage;
import com.ht.pojo.ARewards;
import com.ht.popj.student.StudentReward;
import com.ht.service.student.StudentrewardService;

@Controller
@RequestMapping("android/rewards")
public class RewardsAction {
	
	@Autowired
	StudentrewardService studentrewService;
	@RequestMapping("/get")
	public @ResponseBody ResultMessage Get(Integer stuId){
		List<StudentReward> srList = new ArrayList<StudentReward>();
		srList = studentrewService.selectByStuId(stuId);
		
		List<ARewards> arList = new ArrayList<ARewards>();
		for(StudentReward s : srList){
			ARewards ar = new ARewards();
			ar.setId(s.getId());
			ar.setStudentid(s.getStudentid());
			ar.setCreateTime(s.getCreateTime());
			ar.setReason(s.getReason());
			ar.setContent(s.getContent());
			arList.add(ar);
		}
		ResultMessage rm = new ResultMessage();
		rm.setResultCode("200");
		rm.setResultMessage("³É¹¦£¡");
		rm.setContext(arList);
		return rm;
	}
}
