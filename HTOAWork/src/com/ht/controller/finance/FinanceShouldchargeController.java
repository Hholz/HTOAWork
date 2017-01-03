package com.ht.controller.finance;

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
import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;
import com.ht.popj.finance.FinanceShouldcharge;
import com.ht.popj.finance.FinanceTuitionset;
import com.ht.popj.finance.FinanceType;
import com.ht.popj.finance.Tuitionset;
import com.ht.popj.finance.Tuitionsetdetail;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.education.MajorService;
import com.ht.service.education.TermService;
import com.ht.service.finance.FinanceShouldchargeService;
import com.ht.service.finance.FinanceTuitionSetService;
import com.ht.service.finance.FinanceTypeService;
import com.ht.service.finance.TuitionsetService;
import com.ht.service.finance.TuitionsetdetailService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/Shouldcharge")
public class FinanceShouldchargeController {

	@Autowired 
	FinanceShouldchargeService shouldchargeservice;
	
	@Autowired
	StudentInfoService studentInfoService;
	
	@Autowired 
	StudentService studentService;
	
	@Autowired 
	TermService termService;
	
	@Autowired 
	MajorService majorService;
	
	@Autowired 
	FinanceTypeService financeTypeService;
	
	@Autowired 
	TuitionsetdetailService tuitionsetdetailService;
	
	@Autowired
	TuitionsetService tuitionsetService;
	
	@RequestMapping("/tuitionIndex")
	@SystemControllerLog(description = "进入生成学费页面")
	public String tuitionIndex(Model model) throws Exception{
		//查询所有的届别
		List<StudentFall> falllist = studentInfoService.selectStudentFall();
		model.addAttribute("fallList", falllist);
		//查询所有的专业
		List<EduMajor> majorlist = majorService.selectMajorAll();
		model.addAttribute("majorlist", majorlist);
		return "/finance/tuition";
	}
	
	
	@RequestMapping(value="/Json",method= { RequestMethod.POST })
	public @ResponseBody ResultMessage TuitionJson(int limit, int offset,Tuitionset stu){
		ResultMessage rm = new ResultMessage();
		List<Tuitionset> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(stu !=null){
			sList = tuitionsetService.selectByDynamicStatus(stu);
		}else{
			sList = tuitionsetService.selectByDynamicStatus(stu);
		}
		
		 // 取分页信息
        PageInfo<Tuitionset> pageInfo = new PageInfo<Tuitionset>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//新增学生应收费
	@RequestMapping(value = "/addTuition",method= { RequestMethod.POST }) 
	@SystemControllerLog(description = "新增学生应收费信息")
	public @ResponseBody int addTuition(Tuitionset tui) { 
		int count=0 ;
		//查询该专业下面所有的班级
		StudentClass cla = new StudentClass();
		cla.setLevelId(tui.getFallid().toString());
		cla.setMajorId(tui.getEdudeptid());
		List<StudentClass> classlist = studentInfoService.selectStudentclass(cla);
		int classSize = classlist.size();
		if(classSize!=0){//如果该专业下面有班级（班级不为0）
			for(int i=0;i<classSize;i++){
				//根据每个班级编号，查询该班级下面所有得学生
				int classid = classlist.get(i).getId();
				Student student = new Student();
				student.setClassid(classid);
				List<Student> studlist = studentService.selectByDynamic(student);
				int studSize = studlist.size();
				for(int j=0;j<studSize;j++){
					//给每个学生添加一条应收费记录
					FinanceShouldcharge fee = new FinanceShouldcharge();
					fee.setStuid(studlist.get(j).getId());//学生ID
					fee.setClassid(studlist.get(i).getClassid());//学生班级
					fee.setFallid(tui.getFallid());//学生届别
					fee.setTermid(tui.getTerm());//学期
					fee.setMoney(Float.parseFloat(tui.getSum().toString()));//缴费金额
					fee.setCreateTime(new Date());
					//新增应收费信息
					shouldchargeservice.insert(fee);
				}
			}
			//同时修改学费设置和明细表，状态改为2，以生产记录
			tui.setStatus(2);
			count = tuitionsetService.updateByPrimaryKeySelective(tui);
			
			//Tuitionsetdetail det = new Tuitionsetdetail();
			//det.setStatus(2);
			//det.setTuitionid(tui.getId());
			//count = tuitionsetdetailService.updateByTuitionKeySelective(det);
			
		}else{
			count=-1;//202代表该专业还没有学生
		}
		return count;
	}
	
}
