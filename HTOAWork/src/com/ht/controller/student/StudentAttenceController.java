package com.ht.controller.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.popj.student.StuAttenceCount;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentAttence;
import com.ht.popj.student.StudentClass;
import com.ht.service.student.StudentAttenceService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.util.DateUtil;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;
/*
 * 学生考勤
 */
@Controller
@RequestMapping("/student/attence")
public class StudentAttenceController {
	
	@Autowired
	StudentAttenceService stuAttenService;
	@Autowired
	StudentService studentService;//学生表
	@Autowired
	StudentInfoService studentInfoService;//届别和班级的service
	
	@RequestMapping("/page")
	public String page(Model model){
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		//对应登入人的管理的班级
		if(null != UserInfoUtil.getEmp()){
			//查询当前班主任的非毕业班级
			clsList = studentInfoService.selectOnByClteac(UserInfoUtil.getEmp().getId());
		}
		model.addAttribute("clsList", clsList);
		return "student/studentAttence";
	}
	@RequestMapping("/countPage")
	public String countPage(Model model){
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		//对应登入人的管理的班级
		if(null != UserInfoUtil.getEmp()){
			//查询当前班主任的非毕业班级
			clsList = studentInfoService.selectOnByClteac(UserInfoUtil.getEmp().getId());
		}
		model.addAttribute("clsList", clsList);
		return "student/studentAttenceStatistics";
	}
	@RequestMapping("/attencelistJson")
	public @ResponseBody ResultMessage saystudentheart(int limit, int offset,Model model,StudentAttence attence) throws Exception{
		String createTime = attence.getCreateTime();
		if(null==createTime||"".equals(createTime)){
			createTime = DateUtil.DateToString(new Date()).substring(0, 10);
			attence.setCreateTime(createTime);
		}
		
		ResultMessage rm = new ResultMessage();
		List<StudentAttence> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = stuAttenService.selectAllByPJ(attence);
		 // 取分页信息
        PageInfo<StudentAttence> pageInfo = new PageInfo<StudentAttence>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	//生成某天一个班的所有考勤，默认都是正常
	@RequestMapping("/createTodayAttence")
	public @ResponseBody int createTodayAttence(Integer clsId,String createTime){
		//当时间未空时，默认设置未今天
		if(null==createTime||"".equals(createTime)){
			createTime = DateUtil.DateToString(new Date()).substring(0, 10);
		}
		//查看该天该班是否已生成考勤
		if(stuAttenService.isExistTheDate(clsId,createTime)){
			return -1;
		}
		//通过班级号获取该班所有的学生
		List<Student> stuList = studentService.selectByclsId(clsId);
		//给每一个学生创建当天的所有考勤，默认都是正常
		return stuAttenService.createOneDayAttence(stuList,createTime);
	}
	//修改一个考勤状态(考勤表id，时间（早中晚），考勤情况（正常、迟到..）)
	@RequestMapping("/upOnetimeStatus")
	public @ResponseBody int upOnetimeStatus(Integer id,String time,String state){
		StudentAttence attence = new StudentAttence();
		attence.setId(id);
		if(time.equals("morning")){
			attence.setMorning(state);
		}else if(time.equals("forenoon")){
			attence.setForenoon(state);
		}else if(time.equals("afternoon")){
			attence.setAfternoon(state);
		}else if(time.equals("night")){
			attence.setNight(state);
		}
		return stuAttenService.updateByPJ(attence);
	}
	//考勤统计表json
	/**
	 * @param limit
	 * @param offset
	 * @param model
	 * @param clsId
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stuAttenceCountlistJson")
	public @ResponseBody ResultMessage stuAttenceCountlistJson(int limit, int offset,Model model,Integer clsId,String createTime) throws Exception{
		if(null==createTime||"".equals(createTime)){
			createTime = DateUtil.DateToString(new Date()).substring(0, 7);
		}
		ResultMessage rm = new ResultMessage();
		List<StuAttenceCount> sacList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sacList = stuAttenService.CountAttenByclsId(clsId, createTime);
		//取分页信息
        PageInfo<StuAttenceCount> pageInfo = new PageInfo<StuAttenceCount>(sacList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sacList);
		return rm;
	}
	//一个学生一个月的考勤明细
	@RequestMapping("/stuMonthTbJson")
	public @ResponseBody ResultMessage stuMonth(Model model,Integer stuId,String createTime) throws Exception{
		if(null==createTime||"".equals(createTime)){
			createTime = DateUtil.DateToString(new Date()).substring(0, 7);
		}
		ResultMessage rm = new ResultMessage();
		List<StudentAttence> saList = new ArrayList<StudentAttence>();
		saList = stuAttenService.selectMonthBystuId(stuId, createTime);
		rm.setRows(saList);
		return rm;
	}
}
