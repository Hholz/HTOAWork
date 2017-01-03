package com.ht.controller.flow;


import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.mapper.flow.FlowModeltypeMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentFallMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.flow.FlowAllService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/flowAll")
public class FlowAllController {

	@Autowired
	FlowAllService allService;
	@Autowired
	FlowModeltypeMapper modelTypeMapper;
	@Autowired
	EmpMapper empMapper;
	@Autowired
	DepMapper depMapper;
	@Autowired
	StudentClassMapper classMapper;
	@Autowired
	StudentFallMapper fallMapper;
	@Autowired
	StudentMapper studMapper;
	
	@RequestMapping("/myFlowAll")
	@SystemControllerLog(description = "进入我的申请总览页面")
	public String myFlowAll(Model model) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null != userInfo) {
			if (null != userInfo.getStudent()) {
				List<FlowsModeltype> modeltypes = modelTypeMapper.selectModelTypeName();
				model.addAttribute("modeltypes", modeltypes);
				return "flow/myFlowAllStud";
			}else if(null != userInfo.getEmp()){
				List<FlowsModeltype> modeltypes = modelTypeMapper.selectModelTypeName1();
				model.addAttribute("modeltypes", modeltypes);
				return "flow/myFlowAllEmp";
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	@RequestMapping("/findMyFlowAllStud")
	@SystemControllerLog(description = "返回我的申请总览已经结束的json数据(学生)")
	public @ResponseBody ResultMessage findMyFlowAllStud(int limit, int offset,FlowAll all) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
		}
		ResultMessage rm = new ResultMessage();
		List<FlowAll> alls = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		all.setStudid(student.getId());
		alls = allService.selectFlowAllStud(all);
		 // 取分页信息
        PageInfo<FlowAll> pageInfo = new PageInfo<FlowAll>(alls);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(alls);
		return rm;
	}
	
	@RequestMapping("/findMyFlowAllEmp")
	@SystemControllerLog(description = "返回我的申请总览已经结束的json数据(员工)")
	public @ResponseBody ResultMessage findMyFlowAllEmp(int limit, int offset,FlowAll all) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		ResultMessage rm = new ResultMessage();
		List<FlowAll> alls = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		all.setEmpid(emp.getId());
		alls = allService.selectFlowAllEmp(all);
		 // 取分页信息
        PageInfo<FlowAll> pageInfo = new PageInfo<FlowAll>(alls);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(alls);
		return rm;
	}
	
	@RequestMapping("/flowAllEmp")
	@SystemControllerLog(description = "进入申请总览页面")
	public String flowAllEmp(Model model) throws Exception{
		List<FlowsModeltype> modeltypes = modelTypeMapper.selectModelTypeName1();
		model.addAttribute("modeltypes", modeltypes);
		List<Dep> deps = depMapper.selectDepNameList();
		model.addAttribute("deps", deps);
		return "flow/flowAllEmp";
	}
	
	@RequestMapping("/findFlowAllEmp")
	@SystemControllerLog(description = "返回申请总览已经通过的json数据(员工)")
	public @ResponseBody ResultMessage findFlowAllEmp(int limit, int offset,FlowAll all) {
		ResultMessage rm = new ResultMessage();
		List<FlowAll> alls = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		alls = allService.selectFlowAllEmp1(all);
		 // 取分页信息
        PageInfo<FlowAll> pageInfo = new PageInfo<FlowAll>(alls);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(alls);
		return rm;
	}
	
	@RequestMapping("/findEmpList")
	@SystemControllerLog(description = "返回所选部门的所有员工json数据")
	public @ResponseBody ResultMessage findEmpList(Model model, Dep dep) {
		ResultMessage rm = new ResultMessage();
		List<Emp> emps = new ArrayList<>();
		if (dep.getId() == null) {
			rm.setTotal(1);
			rm.setRows(emps);
			return rm;
		}
		int depid = dep.getId();
		emps = empMapper.selectEmpName(depid);
		rm.setTotal(emps.size());
		rm.setRows(emps);
		return rm;
	}
	
	@RequestMapping("/flowAllStudent")
	@SystemControllerLog(description = "进入申请总览页面")
	public String flowAllStudent(Model model) throws Exception{
		List<FlowsModeltype> modeltypes = modelTypeMapper.selectModelTypeName();
		model.addAttribute("modeltypes", modeltypes);
		List<StudentFall> falls = fallMapper.selectFallList();
		model.addAttribute("falls", falls);
		return "flow/flowAllStud";
	}
	
	@RequestMapping("/findFlowAllStud")
	@SystemControllerLog(description = "返回申请总览已经通过的json数据(员工)")
	public @ResponseBody ResultMessage findFlowAllStud(int limit, int offset,FlowAll all) {
		ResultMessage rm = new ResultMessage();
		List<FlowAll> alls = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		alls = allService.selectFlowAllStud1(all);
		 // 取分页信息
        PageInfo<FlowAll> pageInfo = new PageInfo<FlowAll>(alls);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(alls);
		return rm;
	}
	
	@RequestMapping("/findClassList")
	@SystemControllerLog(description = "返回所选界别的所有班级json数据")
	public @ResponseBody ResultMessage findClassList(Model model, StudentFall fall) {
		ResultMessage rm = new ResultMessage();
		List<StudentClass> classes = new ArrayList<>();
		if (fall.getId() == null) {
			rm.setTotal(1);
			rm.setRows(classes);
			return rm;
		}
		int fallid = fall.getId();
		classes = classMapper.selectClassList(fallid);
		rm.setTotal(classes.size());
		rm.setRows(classes);
		return rm;
	}
	
	@RequestMapping("/findStudentList")
	@SystemControllerLog(description = "返回所选班级中的所有学生json数据")
	public @ResponseBody ResultMessage findStudentList(Model model, StudentClass classes) {
		ResultMessage rm = new ResultMessage();
		List<Student> students = new ArrayList<>();
		if (classes.getId() == null) {
			rm.setTotal(1);
			rm.setRows(students);
			return rm;
		}
		int classid = classes.getId();
		students = studMapper.selectStudentList(classid);
		rm.setTotal(students.size());
		rm.setRows(students);
		return rm;
	}
}
