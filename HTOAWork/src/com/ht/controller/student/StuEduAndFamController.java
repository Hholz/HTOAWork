package com.ht.controller.student;

import java.text.ParseException;
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
import com.ht.popj.student.StudentEdu;
import com.ht.popj.student.StudentFamily;
import com.ht.service.student.StudentEduService;
import com.ht.service.student.StudentFamilyService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;
/*
 * 
 * 学生受教育和家庭成员信息
 */
@Controller
@RequestMapping("/student/edufam")
public class StuEduAndFamController {

	@Autowired
	StudentEduService stuEduService;
	@Autowired
	StudentFamilyService stuFamService;
	@RequestMapping("/page")
	public String page(){
		return "student/stuEduFam";
	}
	@RequestMapping(value = "/page/{id}")
	@SystemControllerLog(description = "通过学生id进入受教育和家庭成员信息页面")
	public String page(Model model,@PathVariable("id")Integer id){
		model.addAttribute("studentid", id);
		return "student/stuEduFam2";
	}
	/*
	 * 学生教育情况表格json数据
	 */
	@RequestMapping("/stuEduListJson")
	@SystemControllerLog(description = "返回学生教育情况表格json数据")
	public @ResponseBody ResultMessage stuEduListJson(int limit, int offset,StudentEdu studentEdu){
		ResultMessage rm = new ResultMessage();
		List<StudentEdu> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(null != studentEdu.getStudentId()){
			sList = stuEduService.selectBystuId(studentEdu.getStudentId());
		}else{
			sList = stuEduService.selectAll();
		}
		 // 取分页信息
        PageInfo<StudentEdu> pageInfo = new PageInfo<StudentEdu>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * 添加学生教育情况
	 */
	@RequestMapping("/addStuEdu")
	@SystemControllerLog(description = "学生受教育信息新增")
	public @ResponseBody int addStuEdu(StudentEdu studentEdu,String bdate,String edate) throws ParseException{ 
		studentEdu.setBegindate(bdate);
		studentEdu.setEnddate(edate);
		//创建时间
		studentEdu.setCreateTime(new Date());// new Date()为获取当前系统时间
		//生成随机密码
		studentEdu.setRemark(RemarkSet.getRemark("添加"));
		if(null!=studentEdu){
			int count = stuEduService.insertByPJ(studentEdu);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	/*
	 * 修改学生教育情况
	 */
	@RequestMapping(value = "edu/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "学生受教育信息修改")
	public @ResponseBody int updateStuEdu(StudentEdu studentEdu,String bdate,String edate) throws ParseException{  
		studentEdu.setBegindate(bdate);
		studentEdu.setEnddate(edate);
		studentEdu.setRemark(RemarkSet.getRemark("更新"));
		studentEdu.setUpdateTime(new Date());
		if(null!=studentEdu){
			int count = stuEduService.updateByPJ(studentEdu);
			return count;
		}
		return 0;
	}
	/*
	 * 删除学生教育情况
	 */
	@RequestMapping(value = "edu/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "学生受教育信息删除")
	public @ResponseBody int deleteStuEdu(Model model,@PathVariable("id")Integer id){  
		int count = stuEduService.delByUpdate(id);
		return count;
	}
	
	
	/*
	 * 学生家庭情况表格json数据
	 */
	@RequestMapping("/stuFamListJson")
	@SystemControllerLog(description = "返回学生家庭情况表格json数据")
	public @ResponseBody ResultMessage stuFamListJson(int limit, int offset,StudentFamily studentFam){
		ResultMessage rm = new ResultMessage();
		List<StudentFamily> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(null != studentFam.getStudentId()){
			sList = stuFamService.selectBystuId(studentFam.getStudentId());
		}else{
			sList = stuFamService.selectAll();
		}
		 // 取分页信息
        PageInfo<StudentFamily> pageInfo = new PageInfo<StudentFamily>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * 添加家庭成员信息
	 */
	@RequestMapping("/addStuFam")
	@SystemControllerLog(description = "学生家庭成员信息新增")
	public @ResponseBody int addStuFam(StudentFamily  studentFam) throws ParseException{ 
		//创建时间
		studentFam.setCreateTime(new Date());// new Date()为获取当前系统时间
		//生成随机密码
		studentFam.setRemark(RemarkSet.getRemark("添加"));
		if(null!=studentFam){
			int count = stuFamService.insertByPJ(studentFam);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	/*
	 * 修改家庭教育情况
	 */
	@RequestMapping(value = "fam/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "学生家庭成员信息修改")
	public @ResponseBody int updateStuFam(StudentFamily  studentFam) throws ParseException{  
		studentFam.setRemark(RemarkSet.getRemark("更新"));
		studentFam.setUpdateTime(new Date());
		if(null!=studentFam){
			int count = stuFamService.updateByPJ(studentFam);
			return count;
		}
		return 0;
	}
	/*
	 * 删除家庭教育情况
	 */
	@RequestMapping(value = "fam/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "学生家庭成员信息删除")
	public @ResponseBody int deleteStuFam(Model model,@PathVariable("id")Integer id){  
		int count = stuFamService.delByUpdate(id);
		return count;
	}
}
