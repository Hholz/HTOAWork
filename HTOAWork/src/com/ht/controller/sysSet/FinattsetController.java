package com.ht.controller.sysSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.ht.popj.sysSet.FinanceAttenceset;
import com.ht.service.sysSet.FinanattenService;
import com.ht.util.ResultMessage;


@Controller
@RequestMapping("/sysSet")
public class FinattsetController {
	
	@Autowired 
	FinanattenService finanattenservice;
	
	@RequestMapping("fattenjsp")
	public String finanattenset(){
		System.out.println("=====");
		return "sysSet/Financeattenceset";
	}

	@RequestMapping("/fattensel")
	@SystemControllerLog(description = "考勤时间controller里的list表")
	public @ResponseBody ResultMessage finanattensel(int limit, int offset,Model model,FinanceAttenceset student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<FinanceAttenceset> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = finanattenservice.financeattensel(student);
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<FinanceAttenceset> pageInfo = new PageInfo<FinanceAttenceset>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//新增上班时间
	@RequestMapping("/fatten/add")
	public @ResponseBody int addfinanceattenset(Model model,FinanceAttenceset student) throws Exception{ 
		if(null!=student){
			String[] t1 = student.getTime1().split(" ");
			String time1 = t1[1];
			String[] t2 = student.getTime2().split(" ");
			String time2 = t2[1];
			String[] t3 = student.getTime3().split(" ");
			String time3 = t3[1];

			student.setTime1(time1);
			student.setTime2(time2);
			student.setTime3(time3);
			
			
			int i =finanattenservice.insertSelective(student);
			return i;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//修改时间
	@RequestMapping(value = "/fatten/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updatefinanceattenset(Model model,FinanceAttenceset student){  
		if(null!=student){
			if(null==student.getTime1()||student.getTime1().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if(null==student.getTime2()||student.getTime2().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if(null==student.getTime3()||student.getTime3().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			String[] t1 = student.getTime1().split(" ");
			String time1 = t1[1];
			String[] t2 = student.getTime2().split(" ");
			String time2 = t2[1];
			String[] t3 = student.getTime3().split(" ");
			String time3 = t3[1];
			student.setTime1(time1);
			student.setTime2(time2);
			student.setTime3(time3);
			
			finanattenservice.updateByPrimaryKeySelective(student);
			System.out.println("p==========p");
			return 1;
		}
		return 0;
	}
	
	@RequestMapping(value = "/qiyong/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updatefinanceattenstat(Model model,FinanceAttenceset student){
		int ids = student.getId();
		List<FinanceAttenceset> sList = finanattenservice.selectidnull(ids);
		for(int i=0;i<sList.size();i++){
			int id = sList.get(i).getId();
			FinanceAttenceset upstat = new FinanceAttenceset();
			upstat.setId(id);
			upstat.setStatus(0);
			finanattenservice.updateByPrimaryKeySelective(upstat);
		}
		finanattenservice.updateByPrimaryKeySelective(student);
		return 1;
	}
	
	//删除学生
	@RequestMapping(value = "/fatten/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deletefinanceattenset(Model model,FinanceAttenceset student){  
		if(null!=student){
			if(null==student.getId()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			finanattenservice.deleteByPrimaryKey(student.getId());
			return 1;
		}
		return 0;
	}
}
