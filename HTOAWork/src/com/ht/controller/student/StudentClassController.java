package com.ht.controller.student;

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
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduMajor;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.education.MajorService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

import common.Logger;
/*
 * 班级设置页面（领导操作）
 */
@Controller
@RequestMapping("/student/class")
public class StudentClassController {

	Logger logger = Logger.getLogger(StudentClassController.class);
	
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	EmpService empservice;
	//专业的service
	@Autowired
	MajorService majorService;
	
	//进入班级设置页面
	@RequestMapping("/page")
	public String studentListclass(Model model,StudentClass sc,Emp emp) throws Exception{
		List<StudentFall> sList = studentInfoService.selectStudentFall();
		List<StudentClass> clist = studentInfoService.selectStudentclass(sc);
		List<Emp> empList = empservice.selectEmp(emp);
		List<EduMajor> majorList = majorService.selectMajorAll();
		model.addAttribute("sList", sList);
		model.addAttribute("clist", clist);
		model.addAttribute("empList", empList);
		model.addAttribute("majorList", majorList);
		return "student/StudentClassinfo";
	}
	//检查班级是否新增过
	@RequestMapping("/check/classname")
	public @ResponseBody boolean studentListclass(Model model,StudentClass studentClass,String classname) throws Exception{
		List<StudentClass> list = studentInfoService.selectStudentclass(studentClass);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@RequestMapping("/classlistJson")
	@SystemControllerLog(description = "班级信息controller里的list表")
	public @ResponseBody ResultMessage classlistJson(int limit, int offset,Model model,StudentClass student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<StudentClass> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = studentInfoService.selectStudentclass(student);
		 // 取分页信息
        PageInfo<StudentClass> pageInfo = new PageInfo<StudentClass>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//新增班级
	@RequestMapping("/add")
	public @ResponseBody int addStudentclass(Model model,StudentClass studentClass,int no,boolean testCls) throws Exception{
		//studentClass.setClassno(st);
		if(null!=studentClass){
			StudentClass cls = new StudentClass();
			cls.setClassname(studentClass.getClassname());
			List<StudentClass> list = studentInfoService.selectStudentclass(cls);
			if(list.size()>0){
				logger.error("该班名已经存在");
				return -1;
			}
			
			if(null==studentClass.getCount()){
				logger.error("没有设置班级人数");
				return 0;
			}
			if(null==studentClass.getLevelId()||studentClass.getLevelId().isEmpty()){
				logger.error("没有设置界别id");
				return 0;
			}
			StudentFall fall = studentInfoService.selectByPrimaryKeyOfFall(Integer.parseInt(studentClass.getLevelId()));
			//String classno = "2106SC01";
			String leave = fall.getLevel().substring(0, 4);
			EduMajor eduMajor = majorService.selectByPrimaryKey(studentClass.getMajorId());
			String major = eduMajor.getCode();
			String num = String.format("%02d",no);
			String classno = leave+major+num;
			logger.info("新增班级，班级编号："+classno);
			if(testCls){
				studentClass.setClsStatus(-1);//试学班
			}else{
				studentClass.setClsStatus(0);//新班
			}
			studentClass.setClassno(classno);
			studentClass.setCreateTime(new Date());
			return studentInfoService.insertSelective(studentClass);
		}
		return 0;
	}
	//修改班级信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentclass(Model model,StudentClass cls,boolean testCls){
		if(null!=cls && null!=cls.getId()){
			if(null==cls.getCount()){
				return 0;
			}else if(cls.getCount()<studentInfoService.countById(cls.getId())){
				return -1;//班级人数设置的比已有人数少，修改失败
			}
			if(testCls){
				cls.setClsStatus(-1);//试学班
			}else{
				cls.setClsStatus(0);//新班
			}
			return studentInfoService.updateByPrimaryKeySelective(cls);
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//删除班级信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentclass(Model model,StudentClass cls){  
		if(null!=cls){
			if(null==cls.getId()){
				logger.error("未传入班级id，无法进行删除操作");
				return 0;
			}else if(studentInfoService.countById(cls.getId())>0){
				logger.error("该班已经分配学生，无法进行删除操作");
				return -1;
			}
			studentInfoService.updateByPrimaryKeySelective(cls);
			return 1;
		}
		return 0;
	}
}
