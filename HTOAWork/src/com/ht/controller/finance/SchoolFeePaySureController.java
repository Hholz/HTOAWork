package com.ht.controller.finance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.education.EduMajor;
import com.ht.popj.finance.FinanceShouldcharge;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.education.MajorService;
import com.ht.service.finance.FinanceShouldchargeService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/schoolFeePaySure")
public class SchoolFeePaySureController {

	@Autowired
	FinanceShouldchargeService financeShouldchargeService;

	@Autowired
	StudentInfoService studentInfoService;

	@Autowired
	MajorService majorService;

	@RequestMapping("/list")
	@SystemControllerLog(description = "进入学生交学费界面")
	public String schoolFeePaySurelist(Model model) throws Exception {
		// 查询所有的届别
		List<StudentFall> falllist = studentInfoService.selectStudentFall();
		model.addAttribute("falllist", falllist);

		List<StudentClass> classlist = studentInfoService.selectStudentclass(new StudentClass());
		model.addAttribute("classlist", classlist);
		// 查询所有的专业
		List<EduMajor> majorlist = majorService.selectMajorAll();
		model.addAttribute("majorlist", majorlist);
		return "/finance/schoolfee_paysure";
	}

	@RequestMapping("/startlist")
	@SystemControllerLog(description = "返回发起反馈通知列表json数据")
	public @ResponseBody ResultMessage list(int limit, int offset, FinanceShouldcharge charge) {
		ResultMessage rm = new ResultMessage();
		List<FinanceShouldcharge> sList = new ArrayList<FinanceShouldcharge>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		sList = financeShouldchargeService.selectAllList(charge);
		// 取分页信息
		PageInfo<FinanceShouldcharge> pageInfo = new PageInfo<FinanceShouldcharge>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
}
