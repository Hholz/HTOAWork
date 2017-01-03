package com.ht.controller.sysSet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.sysSet.ApproveType;
import com.ht.popj.sysSet.FlowModel;
import com.ht.popj.sysSet.FlowModelType;
import com.ht.service.sysSet.ApproveTypeService;
import com.ht.util.ResultMessage;
/*
 * 操作审批类型
 */
@Controller
@RequestMapping("/sysSet/Approvetype")
public class ApproveTypeController {
	
	@Autowired
	ApproveTypeService aptypeservice;
	
	//进入审批类型页面
	@RequestMapping("/AptypeList")
	@SystemControllerLog(description = "进入审批类型页面")
	public String studentList(Model model) {

		return "/sysSet/ApprovetypeList";
	}
	
	//新增审批类型
	@RequestMapping("/add")
	@SystemControllerLog(description = "新增审批类型")
	public @ResponseBody int add(Model model, ApproveType approvetype) {
		int a = aptypeservice.insertSelective(approvetype);
		return a;
	}
	
	//返回审批类型的JSON数据
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "返回审批类型的JSON数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			ApproveType approvetype) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);

		List<ApproveType> flowModelist = aptypeservice.selectByName(approvetype);

		PageInfo<ApproveType> pageInfo = new PageInfo<ApproveType>(flowModelist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(flowModelist);
		return rm;
	}
	
	//修改审批类型
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改审批类型")
	public @ResponseBody int updata(Model model, ApproveType approvetype) {
		aptypeservice.updateByPrimaryKeySelective(approvetype);

		return 1;
	}

	//删除审批类型
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除审批类型")
	public @ResponseBody int delete(Model model, Integer id) {
		aptypeservice.deleteByPrimaryKey(id);
		return 1;
	}
	
}
