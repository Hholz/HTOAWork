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
import com.ht.popj.sysSet.FlowModelType;
import com.ht.service.sysSet.FlowModelTypeService;
import com.ht.util.ResultMessage;
/*
 * 操作物流程类别模板
 */
@Controller
@RequestMapping("/sysSet/FlowmodelType")
public class FlowModelTypeController {
	@Autowired
	FlowModelTypeService floService;
	
	
	//进入流程类别模板页面
	@RequestMapping("/FTypeList")
	@SystemControllerLog(description = "进入流程类别模板页面")
	public String studentList(Model model, Integer id) {
		id=1;
		List<FlowModelType> ftype = floService.findInfo(id);
		model.addAttribute("ftype", ftype);
		return "/sysSet/FlowmodelTypeList";
	}
	
	//新增流程类别
	@RequestMapping("/add")
	@SystemControllerLog(description = "新增流程类别")
	public @ResponseBody int add(Model model, FlowModelType flowModeType) {
		flowModeType.setInvalid(1);
		int a = floService.add(flowModeType);
		return a;
	}

	//返回流程模板的json数据
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "返回流程模板的json数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			FlowModelType flowModeType) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		
		List<FlowModelType> flowModeTypelist = floService.findList(flowModeType);
		
		PageInfo<FlowModelType> pageInfo = new PageInfo<FlowModelType>(flowModeTypelist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(flowModeTypelist);
		return rm;
	}
	
	
	//修改流程类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改流程类别")
	public @ResponseBody int updata(Model model, FlowModelType flowModeType) {
		floService.update(flowModeType);

		return 1;
	}
	
	//删除流程类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除流程类别")
	public @ResponseBody int delete(Model model, Integer id) {
		floService.delete(id);

		return 1;
	}

}
