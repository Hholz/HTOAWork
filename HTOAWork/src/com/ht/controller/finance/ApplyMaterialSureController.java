package com.ht.controller.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.BaoxiaotypeService;
import com.ht.service.dailyWork.EmpService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/applyurse")
public class ApplyMaterialSureController {

	@Autowired
	ApplyMaterialService appmaterialservice;

	@Autowired
	EmpService empservice;

	@Autowired
	BaoxiaotypeService baoxiaotypeservice;

	@RequestMapping("/list")
	@SystemControllerLog(description = "进入报销申请页面")
	public String seletedepList(Model model) {
		List<Emp> emplist = empservice.selectEmp(new Emp());
		model.addAttribute("allEmp", emplist);
		return "finance/applay_sure";
	}

	// 查询报销申请
	@RequestMapping("/statusfivelist")
	@SystemControllerLog(description = "查询报销申请表")
	public @ResponseBody ResultMessage list(int limit, int offset, ApplyMaterial applymaterial) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		applymaterial.setApprovalstatus(5);
		System.out.println(applymaterial);
		List<ApplyMaterial> materiallist = appmaterialservice.selectFinanceID(applymaterial);
		PageInfo<ApplyMaterial> pageInfo = new PageInfo<ApplyMaterial>(materiallist);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(materiallist);
		return rm;
	}
}
