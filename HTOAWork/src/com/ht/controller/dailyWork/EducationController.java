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
import com.ht.popj.dailyWork.Education;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.EducationService;
import com.ht.service.dailyWork.EmpService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/edu")
public class EducationController {
	@Autowired
	EducationService eduservice;
	
	@Autowired
	EmpService empservice;
	
	@RequestMapping("/edulist")
	@SystemControllerLog(description = "进入员工教育背景信息页面")
	public String selectempList(Model model){
		List<Emp> sList = new ArrayList<>();
		Emp emp=new Emp();
		sList=empservice.selectEmp(emp);
		model.addAttribute("emp",sList);
		return "dailyWork/Education";
	}
	
	//查询教育背景
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST }) 
	@SystemControllerLog(description = "查询员工教育背景信息")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id,Integer limit, Integer offset,Education edu) { 
		ResultMessage rm = new ResultMessage();
		List<Education> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = eduservice.selectEducation(edu);
        PageInfo<Education> pageInfo = new PageInfo<Education>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm; 
	}
	
	//新增教育背景
	@RequestMapping(value = "/add") 
	@SystemControllerLog(description = "新增员工教育背景信息")
	public @ResponseBody int addemp(Education edu) { 
		edu.setCreateTime(new Date());
		edu.setUpdateTime(new Date());
		int count=eduservice.insert(edu);
		return count; 
	}
	
	//修改教育背景
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "修改员工教育背景信息")
	public @ResponseBody int updateemp(@PathVariable("id") String id,Education edu) { 
		edu.setUpdateTime(new Date());
		int count=eduservice.updateByPrimaryKey(edu);
		return count; 
	}
	
	//删除教育背景
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }) 
	@SystemControllerLog(description = "删除员工教育背景信息")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) { 
		int count=eduservice.deleteByPrimaryKey(id);
		return count; 
	}
}
