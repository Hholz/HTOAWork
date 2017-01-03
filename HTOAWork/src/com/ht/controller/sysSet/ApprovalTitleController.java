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
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.mapper.sysSet.FlowModelMapper;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.FlowModel;
import com.ht.service.sysSet.ApprovalTitleService;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.util.ResultMessage;
/*
 * 操作审批记录
 */
@Controller
@RequestMapping("/sysSet/Approvaltitle")
public class ApprovalTitleController {
	
	
	@Autowired
	ApprovalTitleService aptitleservice;
	
	@Autowired
	DepMapper depmapper;
	@Autowired
	EmpMapper empmapper;
	@Autowired
	FlowModelMapper flodmapper;
	
	//进入审批记录页面
	@RequestMapping("/ApprovaltitleList")
	@SystemControllerLog(description = "进入审批记录页面")
	public String studentList(Model model) {
		List<Dep> deplist =  depmapper.selectDepList();
		FlowModel f = new FlowModel();
		List<FlowModel> flowlist = flodmapper.selectByName(f);
		model.addAttribute("deplist", deplist);
		model.addAttribute("flowlist", flowlist);
		return "/sysSet/ApprovalTitleList";
	}
	
	//返回所选部门的所有员工的Json数据
	@RequestMapping("/findemp")
	@SystemControllerLog(description = "返回所选部门的所有员工的Json数据")
	public @ResponseBody ResultMessage findemp(Model model,Dep dep) {
		Emp e = new Emp();
		ResultMessage rm = new ResultMessage();
		e.setDepid(dep.getId());
		List<Emp> emplist =  empmapper.selectEmp(e);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	//新增审批记录
	@RequestMapping("/add")
	@SystemControllerLog(description = "新增审批记录")
	public @ResponseBody int add(Model model, ApprovalTitle apptitle) {
		int a = aptitleservice.insertSelective(apptitle);
		return a;
	}
	
	//返回审批记录的json数据
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "返回审批记录的json数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			ApprovalTitle apptitle) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);

		List<ApprovalTitle> flowModelist = aptitleservice.selectByName(apptitle);

		PageInfo<ApprovalTitle> pageInfo = new PageInfo<ApprovalTitle>(flowModelist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(flowModelist);
		return rm;
	}
	
	//修改审批记录
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改审批记录")
	public @ResponseBody int updata(Model model, ApprovalTitle apptitle) {
		aptitleservice.updateByPrimaryKeySelective(apptitle);

		return 1;
	}
	
	//删除审批记录
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除审批记录")
	public @ResponseBody int delete(Model model, Integer id) {
		aptitleservice.deleteByPrimaryKey(id);
		return 1;
	}
	
}
