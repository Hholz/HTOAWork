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
import com.ht.popj.dailyWork.Baoxiao;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.BaoxiaoService;
import com.ht.service.dailyWork.BaoxiaotypeService;
import com.ht.service.dailyWork.EmpService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/reimburse")
public class ReimburseSureController {

	@Autowired
	BaoxiaoService baoxiaoservice;

	@Autowired
	EmpService empservice;

	@Autowired
	BaoxiaotypeService baoxiaotypeservice;

	@RequestMapping("/list")
	@SystemControllerLog(description = "���뱨������ҳ��")
	public String seletedepList(Model model) {
		List<Emp> emplist = empservice.selectEmp(new Emp());
		model.addAttribute("allEmp", emplist);
		return "finance/reimburse_sure";
	}

	// ��ѯ��������
	@RequestMapping("/statusfourlist")
	@SystemControllerLog(description = "��ѯ���������")
	public @ResponseBody ResultMessage list(int limit, int offset, Baoxiao baoxiao) {
		ResultMessage rm = new ResultMessage();
		List<Baoxiao> sList = new ArrayList<Baoxiao>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		PageHelper.startPage(pageCount + 1, limit);
		baoxiao.setFlowstatus(3);
		sList = baoxiaoservice.selectList(baoxiao);
		// ���Ƿ�ҳ��Ϣ
		PageInfo<Baoxiao> pageInfo = new PageInfo<Baoxiao>(sList);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
}
