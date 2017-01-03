package com.ht.controller.student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.ComputerManage;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentComputerApply;
import com.ht.popj.sysSet.FlowModel;
import com.ht.popj.sysSet.FlowModelType;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.student.ComputerManageService;
import com.ht.service.student.StudentComputerService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.service.sysSet.FlowModelService;
import com.ht.service.sysSet.FlowModelTypeService;
import com.ht.service.sysSet.FlowscheduleService;
import com.ht.util.RandomGet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student/scomputer")
public class StudentComputerController {

	@Autowired
	StudentComputerService studentComputerService;
	@Autowired
	StudentService studentService;
	@Autowired
	ComputerManageService computerManageService;
	@Autowired
	FlowModelTypeService floService;
	@Autowired
	FlowModelService fmservice;
	@Autowired
	FlowscheduleService fscheduleservice;
	@Autowired
	StudentInfoService studentInfoService;
	
	Emp e = new Emp();
	

	@RequestMapping("/computerjsp")
	@SystemControllerLog(description = "进入学生电脑维修申请页面")
	public String scomputerapply(Model model, ComputerManage student, FlowModelType flowModeType) {

		List<StudentClass> classlist = new ArrayList<>();
		List<Student> list = new ArrayList<>();
		List<ComputerManage> list2 = new ArrayList<>();

		classlist = studentInfoService.selectallstduentclass();
		list = studentService.selectStudentAll();
		list2 = computerManageService.computermanage(student);
		model.addAttribute("stulist", list);
		model.addAttribute("comlist", list2);
		model.addAttribute("classlist", classlist);
		return "student/StudentComputer";
	}

	//返回所选班级的所有学生的Json数据
	@RequestMapping("findstudent")
	@SystemControllerLog(description = "返回所选班级的所有学生的Json数据")
	public @ResponseBody ResultMessage findemp(Model model, StudentClass dep) {
		Student e = new Student();
		ResultMessage rm = new ResultMessage();
		e.setClassid(dep.getId());
		List<Student> emplist = studentService.selectByDynamic(e);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}

	@RequestMapping("/scomputerlist")
	@SystemControllerLog(description = "学生电脑管理controller里的list表")
	public @ResponseBody ResultMessage StudentComputerList(int limit, int offset, Model model,
			StudentComputerApply student) throws Exception {
		ResultMessage rm = new ResultMessage();
		List<StudentComputerApply> sList = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (student != null) {
			sList = studentComputerService.studentcomputermanage(student);
		}
		System.out.println(sList.size());
		// 取分页信息
		PageInfo<StudentComputerApply> pageInfo = new PageInfo<StudentComputerApply>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		System.out.println("共有学生信息：" + total);
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	// 新增电脑管理类别
	@RequestMapping("/add")
	@SystemControllerLog(description = "新增学生电脑维修申请")
	public @ResponseBody int addStudentComputer(Model model, StudentComputerApply stuconputer) throws Exception {
		stuconputer.setFlowid(31);
		stuconputer.setManagetype(4);
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		
		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
		}
		stuconputer.setCreateman(e.getId());
		int i = studentComputerService.insertSelective(stuconputer);

		return i;
	}

	// 更新电脑管理类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改学生电脑维修申请")
	public @ResponseBody int updateStudentComputer(Model model, StudentComputerApply student) {
		if (null != student) {
			if (null == student.getManagetype() || student.getManagetype()==0) {
				// 返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if (null == student.getRemark() || student.getRemark().isEmpty()) {
				// 返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			studentComputerService.updateByPrimaryKeySelective(student);
			System.out.println("pppp000000000ooo");
			return 1;
		}
		return 0;
	}

	// 删除电脑管理类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除学生电脑维修申请")
	public @ResponseBody int deleteStudentComputer(Model model, StudentComputerApply student) {
		if (null != student) {
			if (null == student.getId()) {
				// 返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			studentComputerService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}

	@RequestMapping(value = "upset")
	@SystemControllerLog(description = "提交电脑维修申请信息")
	public @ResponseBody int upset(Model model, StudentComputerApply applymaterial) {
		// 设置已提交的归还单的审批状态为1
		applymaterial.setStat(1);

		// 生成单号
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(d);
		System.out.println(str);
		str = str.substring(0, 8);
		str = str + RandomGet.getSix() + "DNWXD";
		// 设置单号
		applymaterial.setApplyno(str);

		FlowModel f = new FlowModel();
		// 流程进度
		Flowschedule fsch = new Flowschedule();
		// 办公用品申购流程类别id为1
		f.setFlowmodeltypeid(13);
		// 查询办公用品申购类别的所有流程
		List<FlowModel> flist = fmservice.findList(f);
		for (int i = 0; i < flist.size(); i++) {
			f = flist.get(i);
			fsch.setApplynum(str);
			// 后续流程未完成
			fsch.setFlowstatus(0);
			// 新增时,id为空
			fsch.setId(null);

			fsch.setFlowtype(f.getFlowmodeltypeid());
			fsch.setFlowdot(f.getId());
			fscheduleservice.insertSelective(fsch);
		}

		studentComputerService.updateByPrimaryKeySelective(applymaterial);
		return 1;
	}
}
