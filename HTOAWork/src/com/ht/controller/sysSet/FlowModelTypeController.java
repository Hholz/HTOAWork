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
 * �������������ģ��
 */
@Controller
@RequestMapping("/sysSet/FlowmodelType")
public class FlowModelTypeController {
	@Autowired
	FlowModelTypeService floService;
	
	
	//�����������ģ��ҳ��
	@RequestMapping("/FTypeList")
	@SystemControllerLog(description = "�����������ģ��ҳ��")
	public String studentList(Model model, Integer id) {
		id=1;
		List<FlowModelType> ftype = floService.findInfo(id);
		model.addAttribute("ftype", ftype);
		return "/sysSet/FlowmodelTypeList";
	}
	
	//�����������
	@RequestMapping("/add")
	@SystemControllerLog(description = "�����������")
	public @ResponseBody int add(Model model, FlowModelType flowModeType) {
		flowModeType.setInvalid(1);
		int a = floService.add(flowModeType);
		return a;
	}

	//��������ģ���json����
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "��������ģ���json����")
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
	
	
	//�޸��������
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸��������")
	public @ResponseBody int updata(Model model, FlowModelType flowModeType) {
		floService.update(flowModeType);

		return 1;
	}
	
	//ɾ���������
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ���������")
	public @ResponseBody int delete(Model model, Integer id) {
		floService.delete(id);

		return 1;
	}

}
