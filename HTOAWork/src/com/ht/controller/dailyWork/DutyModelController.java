package com.ht.controller.dailyWork;

import java.util.ArrayList;
import java.util.Date;
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
import com.ht.popj.dailyWork.DutyMaxTemp;
import com.ht.popj.dailyWork.Dutymodel;
import com.ht.popj.dailyWork.Dutymodeldetail;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.StudentFall;
import com.ht.service.dailyWork.DutyModelDService;
import com.ht.service.dailyWork.DutymodelService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.sysSet.FlowscheduleService;
import com.ht.util.DateUtil;
import com.ht.util.ResultMessage;


@Controller
@RequestMapping("/dailyWork/DutyModel")
public class DutyModelController {

	@Autowired
	private EmpService empService;
	@Autowired
	private DutymodelService dutymodelService;
	@Autowired
	private DutyModelDService dutyModelDService;
	@Autowired
	FlowscheduleService fscheduleservice;
	
	@Autowired
	StudentInfoService studentInfoService;
	// ----------------------------------值班模板-------------------------------
	// 值班模板列表
	@RequestMapping("/dutymodelList")
	@SystemControllerLog(description = "进入值班模板和明细信息页面")
	public String dutymodelList(Model model) throws Exception {
		List<Emp> list = new ArrayList<>();
		Emp emp = new Emp();
		list = empService.selectEmp(emp);
		model.addAttribute("emp", list);

		List<Dutymodel> list2 = new ArrayList<>();
		list2 = dutymodelService.findDutymodelList2();
		
		List<StudentFall> fallList = studentInfoService.selectStudentFall();//届别
		model.addAttribute("model", list2);
		model.addAttribute("fallList", fallList);
		return "/dailyWork/Dutymodel";
	}
	//进入查看本周值班
	@RequestMapping("/dutyWeek")
	public String dutyWeek(Model model) throws Exception {
		int modelid = dutymodelService.selectIdIsUsing();//查询当前正在用的模板id
		//查询该模板，某个值班人类型，最大有多少个
		List<DutyMaxTemp> dmtList = dutyModelDService.selectDutyMaxBymodelId(modelid);
		List<Dutymodeldetail> modelDList = dutyModelDService.selectByDutymodelId(modelid);//查询模板明细
		List<String> weksList = dutyModelDService.selectWeksBymodelId(modelid);//用来判断有周几
		model.addAttribute("dmtList", dmtList);
		model.addAttribute("modelDList", modelDList);
		model.addAttribute("weksList", weksList);
		model.addAttribute("weekDays", DateUtil.getWeekOfDate(new Date()));//今天是周几
		return "/dailyWork/DutyWeek";
	}

	@RequestMapping("/dutymodelListJson")
	@SystemControllerLog(description = "返回值班模板表json数据")
	public @ResponseBody ResultMessage dutymodelListJson(int limit, int offset, Dutymodel dutymodel) {
		ResultMessage rm = new ResultMessage();
		List<Dutymodel> list = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (dutymodel != null) {
			list = dutymodelService.findDutymodelList1(dutymodel);
		} else {
			list = dutymodelService.findDutymodelList2();
		}
		// 取分页信息
		PageInfo<Dutymodel> pageInfo = new PageInfo<Dutymodel>(list);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}

	// 新增值班模板
	@RequestMapping("/addDutymodel")
	@SystemControllerLog(description = "新增值班模板")
	public @ResponseBody int addDutymodel(Dutymodel dutymodel) {
		dutymodel.setCreateTime(new Date());
		if (dutymodel != null) {
			int count = dutymodelService.addDutymodel(dutymodel);
			return count;
		}
		return 0;
	}

	// 修改值班模板
	@RequestMapping(value = "/dutymodel/{id}", method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改值班模板")
	public @ResponseBody int updateDutymodel(Dutymodel dutymodel) {
		dutymodel.setUpdateTime(new Date());
		if (dutymodel != null) {
			int count = dutymodelService.updateDutymodel(dutymodel);
			return count;
		}
		return 0;
	}

	// 删除值班模板
	@RequestMapping(value = "/dutymodel/{id}", method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除值班模板")
	public @ResponseBody int deleteDutymodel(@PathVariable("id") Integer id) {
		int count = dutymodelService.deleteDutymodel(id);
		return count;
	}

	// ------------------------值班模板明细----------------------
	// 值班模板明细列表
	@RequestMapping("/modeldetailListJson")
	@SystemControllerLog(description = "返回值班模板明细表json数据")
	public @ResponseBody ResultMessage modeldetailListJson(int limit, int offset, Dutymodeldetail modeldetail) {
		ResultMessage rm = new ResultMessage();
		List<Dutymodeldetail> list = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (modeldetail != null) {
			list = dutyModelDService.selectAll();
		}
		// 取分页信息
		PageInfo<Dutymodeldetail> pageInfo = new PageInfo<Dutymodeldetail>(list);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}

	// 新增值班模板明细
	@RequestMapping("/addModeldetail")
	@SystemControllerLog(description = "新增值班模板明细")
	public @ResponseBody int addModeldetail(Dutymodeldetail modeldetail) {
		modeldetail.setCreateTime(new Date());
		if (modeldetail != null) {
			int count = dutyModelDService.insertByPJ(modeldetail);
			return count;
		}
		return 0;
	}

	// 修改值班模板明细
	@RequestMapping(value = "/modeldetail/{id}", method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改值班模板明细")
	public @ResponseBody int updateModeldetail(Dutymodeldetail modeldetail) {
		modeldetail.setUpdateTime(new Date());
		if (modeldetail != null) {
			int count = dutyModelDService.updateByPJ(modeldetail);
			return count;
		}
		return 0;
	}

	// 删除值班模板明细
	@RequestMapping(value = "/modeldetail/{id}", method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除值班模板明细")
	public @ResponseBody int deleteModeldetail(@PathVariable("id") Integer id) {
		int count = dutyModelDService.deleteById(id);
		return count;
	}
	
	// 启用该模板
	@RequestMapping("/using/{id}")
	@SystemControllerLog(description = "更换当前值班模板")
	public @ResponseBody int using(@PathVariable("id") Integer id) {
		int count = dutymodelService.usingById(id);
		return count;
	}
	
}
