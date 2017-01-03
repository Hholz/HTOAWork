package com.ht.controller.student;

import com.ht.annotation.SystemControllerLog;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduMajor;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.login.ShiroUserRole;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentEdu;
import com.ht.popj.student.StudentFall;
import com.ht.popj.student.StudentFamily;
import com.ht.popj.sysSet.Residence;
import com.ht.popj.sysSet.StuStatus;
import com.ht.service.education.MajorService;
import com.ht.service.login.ShiroUserInfoService;
import com.ht.service.login.ShiroUserService;
import com.ht.service.student.StudentEduService;
import com.ht.service.student.StudentFamilyService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.service.sysSet.ResidenceService;
import com.ht.service.sysSet.StuStatusService;
import com.ht.util.RandomGet;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

import common.Logger;
/*
 * 操作学生的信息
 */
@Controller
@RequestMapping("/student")
public class StudentController {
	
	Logger logger = Logger.getLogger(StudentController.class);
	@Autowired
	StudentService studentService;
	@Autowired
	StudentFamilyService studentFamilyService;
	@Autowired
	StudentEduService studentEduService;
	@Autowired
	ResidenceService residenceService;
	@Autowired
	StudentInfoService studentInfoService;
	
	@Autowired
	ShiroUserService userService;
	@Autowired
	ShiroUserInfoService shiroUserInfoService;
	
	@Autowired
	MajorService majorService;
	
	@Autowired
	StuStatusService stuStatusService;
	
	@RequestMapping("/stuList")
	@SystemControllerLog(description = "进入学生信息页面")
	public String stuList(Model model){
		//获取户口类型
		List<Residence> resList = new ArrayList<Residence>();
		resList = residenceService.selectAll();
		//获取专业类别
		List<EduMajor> majorList = new ArrayList<EduMajor>();
		majorList = majorService.selectMajorAll();
		//获取学生类别
		List<StuStatus> stustaList = new ArrayList<StuStatus>();
		stustaList = stuStatusService.selectAll();
		
		model.addAttribute("resList", resList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("stustaList", stustaList);
		return "student/stuList";
	}
	
	
	@RequestMapping("/selStudent")
	public String selStudent(Model model){
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		clsList = studentInfoService.selectallstduentclass();
		model.addAttribute("clsList", clsList);
		return "student/selStudent";
	}
	
	
	@RequestMapping("/stuListJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "返回学生信息表json数据")
	public @ResponseBody ResultMessage listJson(Integer limit, Integer offset,Student student){
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
	
	
	@RequestMapping("/addStudent")
	@SystemControllerLog(description = "学生新增")
	public @ResponseBody int addStudent(Model model,Student student,Integer clsId) throws ParseException{ 
		//创建时间
		student.setCreateTime(new Date());// new Date()为获取当前系统时间
		student.setPassword("123456");
		student.setRemark(RemarkSet.getRemark("添加学生"));
		//学号
		if(null!=clsId&&clsId!=0){
			StudentClass cls = studentInfoService.selectById(clsId);
			String classno = cls.getClassno();
			int no = studentInfoService.findBigCode(classno);//返回一个班级的最大学号
			String stuno = classno + String.format("%02d",no+1);
			student.setStuno(stuno);
		}
		if(null!=student){
			int count = studentService.addStudent(student);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	
	//更新学生
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "学生修改")
	public @ResponseBody int updateStudent(Model model,Student student,String birDate,String entDate,String oldStuno) throws ParseException{  
	    student.setRemark(RemarkSet.getRemark("更新学生"));
	    student.setUpdateTime(new Date());
	    if(null!=oldStuno&&!"".equals(oldStuno)){
	    	if(null!=student.getStuno()&&!"".equals(student.getStuno())){
	    		String newStuno = student.getStuno();
		    	userService.upNameByName(oldStuno,newStuno);
	    	}
	    }
		if(null!=student){
			int count = studentService.updateStuById(student);
			if(count>0){
				logger.info("修改学生："+student.getStuname()+",学号："+student.getStuno());
			}else{
				logger.error("修改学生"+student.getStuname()+"失败");
			}
			return count;
		}
		logger.error("未传入student信息，新增失败");
		return 0;
	}
	
	
	//删除学生
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "学生删除")
	public @ResponseBody int deleteStudent(Model model,@PathVariable("id")Integer id){  
		int count = studentService.upStaById(id);
		return count;
	}
	
	
	//学生详细信息
	@RequestMapping(value = "info/{id}")
	@SystemControllerLog(description = "进入学生详细信息页面")
	public String first(Model model,@PathVariable("id")Integer id){
		Student student = studentService.selectById(id);
		List<StudentFamily> famList = new ArrayList<StudentFamily>();
		List<StudentEdu> eduList = new ArrayList<StudentEdu>();
		if(null!=student.getId()){
			famList = studentFamilyService.selectBystuId(student.getId());
			eduList = studentEduService.selectBystuId(student.getId());
		}
		model.addAttribute("famList",famList);
		model.addAttribute("eduList",eduList);
		model.addAttribute("student",student);
		return "student/stuInfo";
	}
	
	@RequestMapping("/getStunoByClsId")
	public @ResponseBody String  getStunoByClsId(Model model,Integer clsId){
		StudentClass cls = studentInfoService.selectById(clsId);
		//学生编号
		String classno = cls.getClassno();
		int no = studentInfoService.findBigCode(classno);//返回一个班级的最大学号
		String stuno = classno + String.format("%02d",no+1);
		return stuno;
	}
	
	@RequestMapping("/findStuByNo")
	public @ResponseBody Student  findStuByNo(Model model,String stuno){
		Student studnet = studentService.findStuByNo(stuno);
		return studnet;
	}
}
