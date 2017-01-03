package com.ht.controller.education;

import java.text.SimpleDateFormat;
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
import com.ht.popj.education.EduDept;
import com.ht.popj.education.EduMajor;
import com.ht.service.education.DeptService;
import com.ht.service.education.MajorService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/dept")
public class DeptController {

	@Autowired
	DeptService deptService;
	
	@Autowired
	MajorService majorService;
	
	@RequestMapping("deptIndex")
	@SystemControllerLog(description = "进入系别信息页面")
	public String deptIndex(){
		return "education/dept_Index";
	}
	@RequestMapping("/addDept")
	@SystemControllerLog(description = "新增了一条系别信息")
	public @ResponseBody int  addDept(Model model, EduDept dept){
		//获取系统当前时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		dept.setCreateTime(str);
		int count = deptService.insert(dept);
		
		List<EduDept> deptList = new ArrayList<EduDept>();
		model.addAttribute("mList", deptList);
		return count;
	}
	@RequestMapping("/deptList")
	@SystemControllerLog(description = "查询了系别信息表(Json)")
	public @ResponseBody ResultMessage deptList(int limit, int offset, Model model,EduDept dept){
		ResultMessage rm = new ResultMessage();
		List<EduDept> sList = new ArrayList<EduDept>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		sList = deptService.selectByDynamic(dept);
		// 取分页信息
		PageInfo<EduDept> pageInfo = new PageInfo<EduDept>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改了一条系别信息")
	public @ResponseBody int updateDept(Model model, EduDept dept){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		dept.setUpdateTime(str);
		int count = deptService.updateByPrimaryKeySelective(dept);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除了一条系别信息")
	public @ResponseBody int deleteDept(Model model,@PathVariable("id")Integer id){ 
		int count;
		//如果n大于0，说明这个系下面有专业，不能删除
		int n= majorService.findInfoById(id);
		if(n>0){
			count=2;
		}else{
			count =  deptService.deleteByPrimaryKey(id);
		}
		return count;
	}
	
	@RequestMapping("/findDept")
	public String findDept(Model model){

		List<EduDept> depList = deptService.selectByDynamic(null);
		model.addAttribute("depList", depList);
		return "education/major_Index";
	}
}
