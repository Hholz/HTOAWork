package com.ht.controller.education;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduFeedback;
import com.ht.popj.education.EduFeedbackDetail;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.service.education.FeedBackDetailService;
import com.ht.service.education.FeedBackService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/feedbackdetail")
public class FeedBackDetailController {

	@Autowired
	FeedBackDetailService feedBackDetailService;
	@Autowired
	FeedBackService feedBackService;

	@RequestMapping("/add")
	@SystemControllerLog(description = "新增反馈明细")
	public @ResponseBody int add(EduFeedbackDetail backdetail) {
		String ids = backdetail.getCreateTime();
		String scores = backdetail.getUpdateTime();
		String[] temptaleId = ids.split(",");
		String[] score = scores.split(",");
		int count = 0;
		for (int i = 0; i < score.length; i++) {
			if (backdetail != null) {
				EduFeedbackDetail b = new EduFeedbackDetail();

				b.setScore(Float.parseFloat(score[i]));
				b.setTemplateId(Integer.parseInt(temptaleId[i]));
				b.setFeedbackId(backdetail.getFeedbackId());
				b.setCreateTime(new Date().toLocaleString());
				count = feedBackDetailService.addFeedbackDetail(b);
			}
		}
		return count;
	}

	// bootstrop table 里的url用来获取Json数据
	@RequestMapping("/backdetaillist")
	@SystemControllerLog(description = "返回教员反馈明细json数据")
	public @ResponseBody ResultMessage list(int limit, int offset, Model model, EduFeedbackDetail backdetail) {
		ResultMessage rm = new ResultMessage();
		List<EduFeedbackDetail> sList = new ArrayList<EduFeedbackDetail>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		// 该页数是从1开始（当前页数，一页显示的条数）
		PageHelper.startPage(pageCount + 1, limit);
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null != userInfo.getEmp()) {
			List<EduFeedback> bList = new ArrayList<EduFeedback>();
			EduFeedback back = new EduFeedback();
			Emp emp = userInfo.getEmp();
			back.setEmpId(emp.getId());
			bList = feedBackService.getNoStudentFeedBack(back);
			List<Integer> ids = new ArrayList<Integer>();
			if (bList.size() > 0) {
				for (EduFeedback b : bList) {
					ids.add(b.getId());
				}
				backdetail.setIds(ids);
				sList = feedBackDetailService.getSomeFeedBackDetail(backdetail);
			} else {
				sList = null;
			}
		} else {
			sList = feedBackDetailService.getSomeFeedBackDetail(backdetail);
		}
		// 这是分页信息
		PageInfo<EduFeedbackDetail> pageInfo = new PageInfo<>(sList);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
}
