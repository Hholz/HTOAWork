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
	@SystemControllerLog(description = "����ѧ����ѧ�ѽ���")
	public String schoolFeePaySurelist(Model model) throws Exception {
		// ��ѯ���еĽ��
		List<StudentFall> falllist = studentInfoService.selectStudentFall();
		model.addAttribute("falllist", falllist);

		List<StudentClass> classlist = studentInfoService.selectStudentclass(new StudentClass());
		model.addAttribute("classlist", classlist);
		// ��ѯ���е�רҵ
		List<EduMajor> majorlist = majorService.selectMajorAll();
		model.addAttribute("majorlist", majorlist);
		return "/finance/schoolfee_paysure";
	}

	@RequestMapping("/startlist")
	@SystemControllerLog(description = "���ط�����֪ͨ�б�json����")
	public @ResponseBody ResultMessage list(int limit, int offset, FinanceShouldcharge charge) {
		ResultMessage rm = new ResultMessage();
		List<FinanceShouldcharge> sList = new ArrayList<FinanceShouldcharge>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = financeShouldchargeService.selectAllList(charge);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceShouldcharge> pageInfo = new PageInfo<FinanceShouldcharge>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
}
