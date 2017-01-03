package com.ht.controller.android;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.android.util.ResultMessage;
import com.ht.pojo.AFeedback;
import com.ht.popj.education.EduFeedback;
import com.ht.popj.education.EduFeedbackStart;
import com.ht.popj.student.Student;
import com.ht.service.education.FeedBackService;
import com.ht.service.education.FeedBackStartService;
import com.ht.service.student.StudentService;

@Controller
@RequestMapping("android/feedback")
public class FeedbackAction {

	@Autowired
	StudentService studentService;
	@Autowired
	FeedBackStartService feedBackStartService;
	@Autowired
	FeedBackService feedBackService;
	@RequestMapping("/get")
	public @ResponseBody ResultMessage Get(Integer stuId){
		Student student = studentService.selectById(stuId);
		String nowdate = new Date().toLocaleString();
		EduFeedbackStart start = new EduFeedbackStart();
		start.setEndTime(nowdate);
		List<EduFeedbackStart> blist = feedBackStartService.getSomeFeedBackStart(start);
		
		List<AFeedback> afbList = new ArrayList<AFeedback>();
		List<Integer> ids = new ArrayList();
		int classId = 0;
		if (blist.size() > 0) {
			for (EduFeedbackStart eduFeedbackStart : blist) {
				ids.add(eduFeedbackStart.getClassId());
			}
		}
		if (ids.size() > 0) {
			for (Integer i : ids) {
				if (i == student.getClassid()) {
					classId = i;
				}
			}
		}
		if (classId != 0) {
			start.setClassId(classId);
			EduFeedback back = new EduFeedback();
			back.setStuId(student.getId());
			List<EduFeedback> backList = feedBackService.getSomeFeedBack(back);
			if (backList.size() == 0) {
				blist.clear();
				blist = feedBackStartService.getSomeFeedBackStart(start);
				for(EduFeedbackStart e : blist){
					AFeedback afb = new AFeedback();
					afb.setId(e.getId());
					afb.setClassName(e.getStuClass().getClassname());
					afb.setEmpName(e.getEmpStart().getEmpname());
					afb.setStartEmpName(e.getEmpBeStart().getEmpname());
					afb.setStartTime(e.getStartTime());
					afb.setEndTime(e.getEndTime());
					afbList.add(afb);
				}
				ResultMessage rm = new ResultMessage();
				rm.setResultCode("200");
				rm.setResultMessage("成功！");
				rm.setContext(afbList);
				return rm;
			} else {
				// 此时可能发起了多条反馈通知，但是只反馈了其中一部分条，所以反馈时间小于反馈开始时间则认定为未进行反馈
				// 因为slist可能有多条，遍历的话可能会把时间最小的赋给开始时间，这样会出现错误，导致反馈过的再次反馈，没反馈的反馈不了
				// 但是每次只能有一条
				String feedbacktime = backList.get(0).getFeedbackTime();// 数据库按照时间大的排在前面，所以先取时间最后的反馈完成后再取次后的
				start.setEndTime(null);
				start.setStartTime(feedbacktime);
				// System.out.println(start);
				blist.clear();
				blist = feedBackStartService.getSomeFeedBackStart(start);
				for(EduFeedbackStart e : blist){
					AFeedback afb = new AFeedback();
					afb.setId(e.getId());
					afb.setClassName(e.getStuClass().getClassname());
					afb.setEmpName(e.getEmpStart().getEmpname());
					afb.setStartEmpName(e.getEmpBeStart().getEmpname());
					afb.setStartTime(e.getStartTime());
					afb.setEndTime(e.getEndTime());
					afbList.add(afb);
				}
				ResultMessage rm = new ResultMessage();
				rm.setResultCode("200");
				rm.setResultMessage("成功！");
				rm.setContext(afbList);
				return rm;
			}
		}
		ResultMessage rm = new ResultMessage();
		rm.setResultCode("201");
		rm.setResultMessage("失败！");
		return rm;
	}
}
