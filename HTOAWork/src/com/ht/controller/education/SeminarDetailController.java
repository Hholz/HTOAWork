package com.ht.controller.education;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.education.EduSeminarDatail;
import com.ht.service.education.SeminarDatailService;
import com.ht.service.education.SeminarService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/seminardetail")
public class SeminarDetailController {

	@Autowired
	SeminarDatailService seminarDetailService;
	@Autowired
	SeminarService seminarService;

	@RequestMapping("/add")
	@SystemControllerLog(description="研讨会明细新增修改")
	public @ResponseBody int add(Model model, EduSeminarDatail seminarDetail) {
		if (seminarDetail != null && seminarDetail.getId() != null && seminarDetail.getId() != 0) {
			seminarDetail.setUpdateTime(new Date().toLocaleString());
			int count = seminarDetailService.updateSeminarDatail(seminarDetail);
			return count;
		}else if (seminarDetail != null) {
			seminarDetail.setCreateTime(new Date().toLocaleString());
			int count = seminarDetailService.addSeminarDatail(seminarDetail);
			return count;
		} 
		return 0;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@SystemControllerLog(description="通过研讨会通知ID获取研讨会明细")
	public @ResponseBody ResultMessage get(@PathVariable("id") int id) {
		ResultMessage rm = new ResultMessage();
		EduSeminarDatail detail = seminarDetailService.getSeminarDatail(id);
		rm.setTotal(1);
		rm.setRows(detail);
		return rm;
	}
}