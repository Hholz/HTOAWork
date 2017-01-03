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
import com.ht.popj.education.EduSyllabus;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.service.education.FeedBackService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/feedback")
public class FeedBackController {

	@Autowired
	FeedBackService feedBackService;

	@RequestMapping("/list")
	@SystemControllerLog(description = "���뷴���б�ҳ��")
	public String list() {

		return "/education/feedback";
	}

	@RequestMapping("/add")
	@SystemControllerLog(description = "��������")
	public @ResponseBody int add(EduFeedback back) {
		if (back != null) {
			back.setCreateTime(new Date().toLocaleString());
			int count = feedBackService.addFeedback(back);
			return back.getId();
		}
		return 0;
	}

	// bootstrop table ���url������ȡJson����
	@RequestMapping("/backbacklist")
	@SystemControllerLog(description = "���ؽ�Ա����json����")
	public @ResponseBody ResultMessage list(int limit, int offset, Model model, EduFeedback back) {
		ResultMessage rm = new ResultMessage();
		List<EduFeedback> sList = new ArrayList<EduFeedback>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		PageHelper.startPage(pageCount + 1, limit);
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null != userInfo.getEmp()) {
			Emp emp = userInfo.getEmp();
			back.setEmpId(emp.getId());
			sList = feedBackService.getNoStudentFeedBack(back);
		} else {
			sList = feedBackService.getSomeFeedBack(back);
		}
		// ���Ƿ�ҳ��Ϣ
		PageInfo<EduFeedback> pageInfo = new PageInfo<>(sList);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
}
