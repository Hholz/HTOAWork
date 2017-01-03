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
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Works;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.dailyWork.WorksService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/works")
public class WorksController {
	@Autowired
	WorksService worksservice;
	
	@Autowired
	EmpService empservice;
	
	@RequestMapping("/workslist")
	@SystemControllerLog(description = "进入员工工作经历信息页面")
	public String selectempList(Model model){
		List<Emp> sList = new ArrayList<>();
		Emp emp=new Emp();
		sList=empservice.selectEmp(emp);
		model.addAttribute("emp",sList);
		return "dailyWork/Works";
	}
	
	//查询工作经历
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST }) 
	@SystemControllerLog(description = "查询员工工作经历")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id,Integer limit, Integer offset,Works works) { 
		ResultMessage rm = new ResultMessage();
		List<Works> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = worksservice.selectWorks(works);
        PageInfo<Works> pageInfo = new PageInfo<Works>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm; 
	}
	
	//新增工作经历
	@RequestMapping(value = "/add") 
	@SystemControllerLog(description = "新增员工工作经历")
	public @ResponseBody int addemp(Works works) { 
		works.setCreateTime(new Date());
		works.setUpdateTime(new Date());
		int count=worksservice.insert(works);
		return count; 
	}
	
	//修改工作经历
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "修改员工工作经历")
	public @ResponseBody int updateemp(@PathVariable("id") String id,Works works) { 
		works.setUpdateTime(new Date());
		int count=worksservice.updateByPrimaryKey(works);
		return count; 
	}
	
	//删除工作经历
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }) 
	@SystemControllerLog(description = "删除员工工作经历")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) { 
		int count=worksservice.deleteByPrimaryKey(id);
		return count; 
	}
}
