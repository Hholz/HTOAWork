package com.ht.controller.dailyWork;

import java.text.SimpleDateFormat;
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
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Attendancecount;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.AttendanceService;
import com.ht.service.dailyWork.AttendancecountService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/AttendanceCount")
public class AttendanceCountController {

	@Autowired
	DepMapper depmapper;
	@Autowired
	EmpMapper empmapper;
	@Autowired
	AttendanceService attendanceService;
	@Autowired
	AttendancecountService attcountservice;

	@RequestMapping("/AttendanceCountList")
	@SystemControllerLog(description = "查看考勤统计页面页面")
	public String List(Model model) {
		List<Dep> deplist = depmapper.selectDepList();
		Emp e = new Emp();
		List<Emp> emplist = empmapper.selectEmp(e);

		model.addAttribute("deplist", deplist);
		model.addAttribute("emplist", emplist);

		return "/dailyWork/AttendanceCountList";
	}

	// 返回所选部门的所有员工的Json数据
	@RequestMapping("/findemp")
	@SystemControllerLog(description = "返回所选部门的所有员工的Json数据")
	public @ResponseBody ResultMessage findemp(Model model, Dep dep) {
		Emp e = new Emp();
		ResultMessage rm = new ResultMessage();
		e.setDepid(dep.getId());
		List<Emp> emplist = empmapper.selectEmp(e);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}

	@RequestMapping("/add")
	@SystemControllerLog(description = "新增考勤统计")
	public @ResponseBody int add(Model model, Attendancecount applymaterial) {
		Emp emp = null;
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		String str = sdf.format(d);
		System.out.println(str);
		applymaterial.setTime(str);
		List<Attendancecount> attlist = attcountservice.selectByName(applymaterial);
		if (!attlist.isEmpty()) {
			return 0;
		}

		List<Emp> eList = empmapper.selectEmp(emp);
		for (int i=0;i<eList.size();i++) {
			Emp e = eList.get(i);
			applymaterial.setId(null);
			applymaterial.setEmpid(e.getId());
			int hickcount = attcountservice.selecthickcount(applymaterial);// 病假次数
			int thinfcount = attcountservice.selectthingcount(applymaterial);// 事假次数
			int latecount = attcountservice.selecttlatecount(applymaterial);// 迟到次数
			int misscount = attcountservice.selecttmissworkcount(applymaterial);// 旷工次数
			int dutycount = attcountservice.selecttdutycount(applymaterial);
			int overcount = attcountservice.selecttovercount(applymaterial);
			applymaterial.setOverworkcount(overcount);
			applymaterial.setScikcount(hickcount);
			applymaterial.setLatecount(latecount);
			applymaterial.setCasualcount(thinfcount);
			applymaterial.setMissworkcount(misscount);
			applymaterial.setDutycount(dutycount);
			attcountservice.insertSelective(applymaterial);
		}
		return 1;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "返回考勤统计表json数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			Attendancecount applymaterial) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);

		List<Attendancecount> materiallist = attcountservice.selectByName(applymaterial);

		PageInfo<Attendancecount> pageInfo = new PageInfo<Attendancecount>(materiallist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(materiallist);
		return rm;
	}
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改考勤统计信息")
	public @ResponseBody int updata(Model model, Attendancecount applymaterial) {
		attcountservice.updateByPrimaryKeySelective(applymaterial);
		return 1;
	}
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除考勤统计信息")
	public @ResponseBody int delete(Model model, Integer id) {
		attcountservice.deleteByPrimaryKey(id);
		return 1;
	}
}
