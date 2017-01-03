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
import com.ht.mapper.sysSet.FlowModelTypeMapper;
import com.ht.popj.sysSet.FlowModel;
import com.ht.popj.sysSet.FlowModelType;
import com.ht.service.sysSet.FlowModelService;
import com.ht.util.ResultMessage;
/*
 * 操作流程
 */
@Controller
@RequestMapping("/sysSet/FlowModel")
public class FlowModelController {

	@Autowired
	FlowModelService fmS;
	@Autowired
	FlowModelTypeMapper fdtMapper;
	
	//查询所有流程类别,进入流程页面
	@RequestMapping("/FModeList")
	@SystemControllerLog(description = "查询所有流程类别,进入流程页面")
	public String studentList(Model model) {
		List<FlowModelType> flowmodeltypeList = fdtMapper.selectList();

		model.addAttribute("flowmodeltypeList", flowmodeltypeList);
		return "/sysSet/FlowmodelList";
	}
	
	//新增流程信息
	@RequestMapping("/add")
	@SystemControllerLog(description = "新增流程信息")
	public @ResponseBody int add(Model model, FlowModel flowMode) {
		int a = fmS.add(flowMode);
		return a;
	}
	
	//返回流程的Json数据
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "返回流程的Json数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			FlowModel flowMode) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);

		List<FlowModel> flowModelist = fmS.findList(flowMode);

		PageInfo<FlowModel> pageInfo = new PageInfo<FlowModel>(flowModelist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(flowModelist);
		return rm;
	}

	//修改流程信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改流程信息")
	public @ResponseBody int updata(Model model, FlowModel flowMode) {
		fmS.update(flowMode);

		return 1;
	}
	
	//删除流程信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除流程信息")
	public @ResponseBody int delete(Model model, Integer id) {
		fmS.delete(id);
		return 1;
	}

}
