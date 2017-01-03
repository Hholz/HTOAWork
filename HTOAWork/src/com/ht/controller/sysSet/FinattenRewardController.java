package com.ht.controller.sysSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.student.StudentReward;
import com.ht.popj.sysSet.FinanceAttencerewardset;
import com.ht.service.sysSet.FinattenRewardService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/sysSet")
public class FinattenRewardController {
	
	@Autowired
	FinattenRewardService finattenRewardService;
	
	@RequestMapping("/frewardjsp")
	public String FinanatterReward(){
		return "sysSet/FinattenRewardset";
	}

	//新增学生
	@RequestMapping("/ftreward/add")
	public @ResponseBody int addStudentreward(Model model,FinanceAttencerewardset student) throws Exception{ 
		if(null!=student){
			if(null==student.getLatesalary()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if(null==student.getRemark()||student.getRemark().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			int i =finattenRewardService.insertSelective(student);
			return i;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping("/ftrewardset")
	@SystemControllerLog(description = "考勤时间的controller里的list表")
	public @ResponseBody ResultMessage saystudentheart(int limit, int offset,Model model,FinanceAttencerewardset student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<FinanceAttencerewardset> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = finattenRewardService.finattenrewardsel(student);
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<FinanceAttencerewardset> pageInfo = new PageInfo<FinanceAttencerewardset>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//修改薪资
	@RequestMapping(value = "/ftreward/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentreward(Model model,FinanceAttencerewardset student){  
		if(null!=student){
			if(null==student.getLatesalary()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if(null==student.getSickleavesalary()||student.getSickleavesalary().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			finattenRewardService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
		
	//删除薪资记录
	@RequestMapping(value = "/ftreward/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentreward(Model model,FinanceAttencerewardset student){  
		if(null!=student){
			if(null==student.getId()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			finattenRewardService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
	
}
