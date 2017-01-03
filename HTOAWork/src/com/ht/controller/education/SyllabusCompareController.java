package com.ht.controller.education;

import java.util.ArrayList;
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
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduCourse;
import com.ht.popj.education.EduCourseTeacher;
import com.ht.popj.education.EduSyllabus;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.StudentClass;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.education.CourseService;
import com.ht.service.education.CourseTeacherService;
import com.ht.service.education.SyllabusRecordService;
import com.ht.service.education.SyllabusService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/syllabuscompare")
public class SyllabusCompareController {

	@Autowired
	SyllabusRecordService syllabusRecordService;

	@Autowired
	SyllabusService syllabusService;

	@Autowired
	EmpService empservice;

	@Autowired
	CourseTeacherService courseTeacherService;

	@Autowired
	CourseService courseService;

	@Autowired
	StudentInfoService studentInfoService;

	@RequestMapping("/list")
	@SystemControllerLog(description="����γ̽���ҳ��")
	public String list(Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			EduCourseTeacher t = new EduCourseTeacher();
			t.setEmpId(emp.getId());
			//if (courseTeacherService.selectByDynamic(t).size() == 0) {
				List<Emp> allEmp = empservice.selectEmp(new Emp());
				List<EduCourse> allCourse = courseService.selectByDynamic(new EduCourse());
				List<StudentClass> allClass = studentInfoService.selectStudentclass(new StudentClass());
				model.addAttribute("allEmp", allEmp);
				model.addAttribute("allCourse", allCourse);
				model.addAttribute("allClass", allClass);
			/*} else {
				EduCourseTeacher courseTeacher = new EduCourseTeacher();
				courseTeacher.setEmpId(emp.getId());
				List<EduCourseTeacher> teacherCourse = courseTeacherService.selectByDynamic(courseTeacher);
				model.addAttribute("teacherCourse", teacherCourse);

				StudentClass record = new StudentClass();
				record.setTeacherId(emp.getId());
				List<StudentClass> someClass = studentInfoService.selectStudentclass(record);
				model.addAttribute("allClass", someClass);
				List<Emp> allEmp = empservice.selectEmp(emp);
				model.addAttribute("oneEmp", allEmp);
			}*/
		}
		return "/education/syllabus_compare";
	}

	@RequestMapping(value = "/getClassByEmpId", method = { RequestMethod.PUT })
	@SystemControllerLog(description="ͨ��Ա��ID��ȡ�༶��Ϣ")
	public @ResponseBody ResultMessage getClassByEmpId(Emp emp) {
		ResultMessage rm = new ResultMessage();
		List<StudentClass> someClass = new ArrayList<StudentClass>();
		StudentClass record = new StudentClass();
		record.setTeacherId(emp.getId());
		someClass = studentInfoService.selectStudentclass(record);
		rm.setTotal(someClass.size());
		rm.setRows(someClass);
		return rm;
	}

	@RequestMapping(value = "/getCourseByEmpId", method = { RequestMethod.PUT })
	@SystemControllerLog(description="ͨ��Ա��ID��ȡ�γ���Ϣ")
	public @ResponseBody ResultMessage getCourseByEmpId(Emp emp) {
		ResultMessage rm = new ResultMessage();
		EduCourseTeacher courseTeacher = new EduCourseTeacher();
		courseTeacher.setEmpId(emp.getId());
		List<EduCourseTeacher> teacherCurse = courseTeacherService.selectByDynamic(courseTeacher);
		rm.setTotal(teacherCurse.size());
		rm.setRows(teacherCurse);
		return rm;
	}

	@RequestMapping("/getSyllabus")
	@SystemControllerLog(description="��ȡ�ƻ�������ʵ�ʽ�������")
	public @ResponseBody ResultMessage getSyllabus(int limit, int offset, Model model, EduSyllabus syllabus) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		/*if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			EduCourseTeacher t = new EduCourseTeacher();
			t.setEmpId(emp.getId());
			if (courseTeacherService.selectByDynamic(t).size() != 0) {
				syllabus.setEmpId(emp.getId());//����ǽ�Ա����ֻ�ܲ鿴�Լ��Ľ���
			}
		}*/
		ResultMessage rm = new ResultMessage();
		List<EduSyllabus> sList = new ArrayList<EduSyllabus>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		PageHelper.startPage(pageCount + 1, limit);
		System.out.println(syllabus);
		if (syllabus != null) {
			sList = syllabusService.getPlanSyllabus(syllabus);
		}
		// ���Ƿ�ҳ��Ϣ
		PageInfo<EduSyllabus> pageInfo = new PageInfo<>(sList);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
}
