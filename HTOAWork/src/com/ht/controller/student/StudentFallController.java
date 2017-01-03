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
import com.ht.popj.student.StudentFall;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student/fall")
public class StudentFallController {

	@Autowired
	StudentInfoService studentInfoService;
	//进入届别页面
	@RequestMapping("/page")
	public String studentListfall(){
		return "student/Studentfall";
	}
	
	@RequestMapping("/fallListJson")
	@SystemControllerLog(description = "学生届别controller里的list表")
	public @ResponseBody ResultMessage listJsonfall(int limit, int offset,Model model,StudentFall student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<StudentFall> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = studentInfoService.selectByDynamic(student);
		}else{
			sList = studentInfoService.selectStudentFall();
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<StudentFall> pageInfo = new PageInfo<StudentFall>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//新增界别
	@RequestMapping("/add")
	public @ResponseBody int addStudentfall(Model model,StudentFall student) throws Exception{ 
		if(null!=student){
			if(null==student.getLevel()||student.getLevel().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			studentInfoService.insertSelective(student);
			return 1;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//修改界别
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentfall(Model model,StudentFall student){  
		if(null!=student){
			if(null==student.getLevel()||student.getLevel().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}			
			studentInfoService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
	
	//删除界别
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentfall(Model model,StudentFall student){  
		if(null!=student){
			if(null==student.getId()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}			
			studentInfoService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
}
