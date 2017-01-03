package com.ht.controller.conn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.market.MarketStudent;
import com.ht.popj.student.StuReplyModel;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.service.market.MarketStudentService;
import com.ht.service.student.StuReplyModelService;
import com.ht.service.student.StudentService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

@Controller
@RequestMapping("/conn")
public class GetStuController {

	@Autowired
	StudentService studentService;
	@Autowired
	MarketStudentService msService;
	@Autowired
	StuReplyModelService srmService;
	
	@RequestMapping("/stuListJson")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Student student){
		ResultMessage rm = new ResultMessage();
		List<Student> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = studentService.selectByDynamic(student);
		}else{
			sList = studentService.selectStudentAll();
		}
		 // 取分页信息
        PageInfo<Student> pageInfo = new PageInfo<Student>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
		
	// 待分班学生列表
	@RequestMapping("/intentStuListJson")
	@SystemControllerLog(description = "返回正式报名的学生表json数据")
	public @ResponseBody ResultMessage intentionStudentListJson(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		marketStudent.setMsStatus(2);//正式报名的学生
		if (marketStudent != null) { 
			list = msService.selectPredStudentAll(marketStudent);
		}
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	// 待分班学生列表
	@RequestMapping("/intentStuListJsonRand")
	@SystemControllerLog(description = "返回正式报名的MarketStudent表json数据")
	public @ResponseBody ResultMessage intentStuListJsonRand(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		list = msService.selectPredStudentAllRand();
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	// 待试学学生列表
	@RequestMapping("/testStuListJson")
	@SystemControllerLog(description = "返回试学状态的MarketStudent表json数据")
	public @ResponseBody ResultMessage testStuListJsonRand(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		list = msService.selectTestStudentAll();
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	//通过界别id来获取所有班级
	@RequestMapping(value = "/stuBySrmId/{id}")
	public @ResponseBody List<Student> stuBySrmId(@PathVariable("id")Integer id) throws Exception{  
		List<Student> stuList = new ArrayList<Student>();
		//先查通过srmId查班级
		StuReplyModel srm = srmService.selectById(id);
		int clsId = 0;
		if(null!=srm.getClsId()){
			clsId = srm.getClsId();
		}
		//通过班级id查student
		Student stuTemp = new Student();
		stuTemp.setClassid(clsId);
		stuList = studentService.selectByDynamic(stuTemp);
		return stuList;
	}
	//通过班级id来获取所有班级
	@RequestMapping(value = "/stuByClsId/{id}")
	public @ResponseBody List<Student> stuByClsId(@PathVariable("id")Integer id) throws Exception{  
		List<Student> stuList = new ArrayList<Student>();
		//通过班级id查student
		Student stuTemp = new Student();
		stuTemp.setClassid(id);
		stuList = studentService.selectByDynamic(stuTemp);
		return stuList;
	}
}
