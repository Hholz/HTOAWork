package com.ht.controller.student;

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
import com.ht.popj.student.ComputerManage;
import com.ht.popj.student.StudentSayface;
import com.ht.service.student.ComputerManageService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student")
public class ComputerManageController {
	
	@Autowired
	ComputerManageService computerManageService;
	
	@RequestMapping("/cmanagejsp")
	public String computermanagejsp(){
		return "student/ComputermajorList";
	}

	@RequestMapping("/cmanagelist")
	@SystemControllerLog(description = "电脑管理controller里的list表")
	public @ResponseBody ResultMessage computermanageList(int limit, int offset,Model model,ComputerManage student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<ComputerManage> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = computerManageService.computermanage(student);
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<ComputerManage> pageInfo = new PageInfo<ComputerManage>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//新增电脑管理类别
	@RequestMapping("/cmanage/add")
	public @ResponseBody int addComputermanage(Model model,ComputerManage student) throws Exception{ 
		if(null!=student){
			if(null==student.getManagetype()||student.getManagetype().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			int i =computerManageService.insertSelective(student);
			return i;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//更新电脑管理类别
	@RequestMapping(value = "/cmanage/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateComputermanage(Model model,ComputerManage student){  
		if(null!=student){
			if(null==student.getManagetype()||student.getManagetype().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if(null==student.getRemark()||student.getRemark().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			computerManageService.updateByPrimaryKeySelective(student);
			System.out.println("pppp000000000ooo");
			return 1;
		}
		return 0;
	}
	
	//删除电脑管路类别
	@RequestMapping(value = "/cmanage/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteComputermanage(Model model,ComputerManage student){  
		if(null!=student){
			if(null==student.getId()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			computerManageService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
}
