package com.ht.controller.dailyWork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
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
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Worklog;
import com.ht.popj.education.EduCourse;
import com.ht.popj.education.EduCourseQuery;
import com.ht.popj.education.EduOutline;
import com.ht.popj.education.EduSyllabusRecord;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.StudentClass;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.dailyWork.WorklogService;
import com.ht.service.education.CourseService;
import com.ht.service.education.CourseTeacherService;
import com.ht.service.education.OutlineService;
import com.ht.service.education.SyllabusRecordService;
import com.ht.service.education.SyllabusService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/Worklog")
public class WorklogController {

	@Autowired
	WorklogService Worklogservice;

	@Autowired
	EmpService empservice;

	@Autowired
	CourseService courseService;

	@Autowired
	OutlineService outlineService;

	@Autowired
	StudentInfoService studentInfoService;

	@Autowired
	CourseTeacherService teacherService;

	@Autowired
	SyllabusRecordService syllabusRecoreService;

	@Autowired
	SyllabusService syllabusService;

	@RequestMapping("/Workloglist")
	@SystemControllerLog(description = "����Ա��������־ҳ��")
	public String seletedepList(Model model) {
		List<Emp> sList = empservice.selectEmp(new Emp());// ����Ա��
		List<EduCourse> course = courseService.selectByDynamic(new EduCourse());// ���пγ�
		List<EduOutline> outline = outlineService.selectByDynamic(new EduOutline());// ���пγ̴��
		List<StudentClass> classes = studentInfoService.selectallstduentclass();// ���а༶
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			model.addAttribute("userid", emp.getId());
		}
		// Map map = new HashMap();
		// map.put("empId", emp.getId());
		// List<EduCourseQuery> educourse = syllabusService.getCourse(map);//
		// �õ�¼Ա�����̵Ŀγ�
		StudentClass educl = new StudentClass();
		educl.setTeacherId(emp.getId());
		List<StudentClass> educlass = studentInfoService.selectStudentclass(educl);// �õ�¼Ա�����̵Ŀγ�
		// model.addAttribute("educourse", educourse);
		model.addAttribute("educlass", educlass);
		model.addAttribute("classes", classes);
		model.addAttribute("emp", sList);
		model.addAttribute("course", course);
		model.addAttribute("outline", outline);
		return "dailyWork/Worklog";
	}

	@RequestMapping("/writelog")
	@SystemControllerLog(description = "����Ա��д������־ҳ��")
	public String writelog(Model model) {
		List<Emp> sList = empservice.selectEmp(new Emp());// ����Ա��
//		List<EduCourse> course = courseService.selectByDynamic(new EduCourse());// ���пγ�
//		List<EduOutline> outline = outlineService.selectByDynamic(new EduOutline());// ���пγ̴��
//		List<StudentClass> classes = studentInfoService.selectallstduentclass();// ���а༶
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			model.addAttribute("userid", emp.getId());
		}
		// Map map = new HashMap();
		// map.put("empId", emp.getId());
		// List<EduCourseQuery> educourse = syllabusService.getCourse(map);//
		// �õ�¼Ա�����̵Ŀγ�
		StudentClass educl = new StudentClass();
		educl.setTeacherId(emp.getId());
		List<StudentClass> educlass = studentInfoService.selectStudentclass(educl);// �õ�¼Ա�����̵Ŀγ�
//		// model.addAttribute("educourse", educourse);
		model.addAttribute("educlass", educlass);
//		model.addAttribute("classes", classes);
		model.addAttribute("emp", sList);
//		model.addAttribute("course", course);
//		model.addAttribute("outline", outline);
		return "dailyWork/WorklogWrite";
	}
	
	// ��ѯԱ��������־
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "��ѯԱ��������־��Ϣ")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id, Integer limit, Integer offset,
			Worklog worklog) {
		ResultMessage rm = new ResultMessage();
		List<Worklog> sList = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = Worklogservice.selectWorklogList(worklog);
		PageInfo<Worklog> pageInfo = new PageInfo<Worklog>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		System.out.println("����Ա��������־��" + total);
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	// ����Ա��������־
	@RequestMapping(value = "/add")
	@SystemControllerLog(description = "����Ա��������־��Ϣ")
	public @ResponseBody int addemp(Worklog worklog) {
		worklog.setWorkday(new Date().toLocaleString());
		worklog.setCreateTime(new Date());
		worklog.setUpdateTime(new Date());
		int count = Worklogservice.insert(worklog);
		if (count > 0 && worklog.getClassid() != null && worklog.getClassid() != 0 && worklog.getCourseid() != null
				&& worklog.getCourseid() != 0 && worklog.getProcess() != null && worklog.getProcess() != 0) {
			EduSyllabusRecord record = new EduSyllabusRecord();
			record.setCreateTime(new Date().toLocaleString());
			record.setEmpId(worklog.getEmpid());
			record.setHour(worklog.getHour());
			record.setClassId(worklog.getClassid());
			record.setCourseId(worklog.getCourseid());
			record.setRemark(worklog.getRemark());
			record.setOutlineId(worklog.getProcess());
			count = syllabusRecoreService.addSyllabusRecord(record);
		}
		return count;
	}

	// �޸�Ա��������־
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸�Ա��������־��Ϣ")
	public @ResponseBody int updateemp(@PathVariable("id") String id, Worklog worklog) throws Exception {
		long i = gettime((Worklogservice.selectByPrimaryKey(worklog.getId()).getCreateTime()));
		int count = -1;
		if (i < 1) {
			worklog.setUpdateTime(new Date());
			count = Worklogservice.updateByPrimaryKey(worklog);
		}
		EduSyllabusRecord record = new EduSyllabusRecord();
		record.setUpdateTime(new Date().toLocaleString());
		record.setEmpId(worklog.getEmpid());
		record.setHour(worklog.getHour());
		record.setClassId(worklog.getClassid());
		record.setCourseId(worklog.getCourseid());
		record.setRemark(worklog.getRemark());
		record.setOutlineId(worklog.getProcess());
		if (count > 0) {
			syllabusRecoreService.updateSyllabusRecord(record);
		}
		return count;
	}

	// ɾ��Ա��������־
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ��Ա��������־��Ϣ")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) throws Exception {
		long i = gettime((Worklogservice.selectByPrimaryKey(id).getCreateTime()));
		int count = -2;
		if (i < 1) {
			count = Worklogservice.deleteByPrimaryKey(id);
		}
		return count;
	}

	// ��ѯ�γ̵Ĵ��
	@RequestMapping(value = "/findprocess/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "Ա��������־��Ϣ--����������--���ݿγ̲�ѯ���")
	public @ResponseBody ResultMessage findprocess(@PathVariable("id") Integer id) {
		ResultMessage rm = new ResultMessage();
		List<EduOutline> outlinelist = new ArrayList<>();
		EduOutline outline = new EduOutline();
		if (id > 0) {
			outline.setCourseId(id);
			outlinelist = outlineService.selectByDynamic(outline);
		} else {
			outlinelist = outlineService.selectByDynamic(outline);
		}
		rm.setTotal(outlinelist.size());
		rm.setRows(outlinelist);
		return rm;
	}

	// ��ѯ�γ̵Ŀ�ʱ
	@RequestMapping(value = "/findhour/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "Ա��������־��Ϣ--����������--���ݿγ̲�ѯ��ʱ")
	public @ResponseBody Integer findhour(@PathVariable("id") Integer id, Worklog worklog) {
		EduOutline outline = new EduOutline();
		outline.setCourseId(id);
		int hour = 0;
		String count = Worklogservice.selectnexthour(worklog);
		if (null != count) {
			hour = Integer.parseInt(count);
		}
		System.out.println("" + count);
		return hour + 1;
	}

	public long gettime(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		Date sdf1 = new SimpleDateFormat("yyyy-MM-dd").parse((new Date()).toLocaleString());
		Date sdf = new SimpleDateFormat("yyyy-MM-dd").parse(date.toLocaleString());
		cal.setTime(sdf);
		Date d1 = cal.getTime();
		cal.setTime(sdf1);
		Date d2 = cal.getTime();
		long daterange = d2.getTime() - d1.getTime();
		long time = 1000 * 3600 * 12;
		return daterange / time;
	}

	@RequestMapping(value = "/getCourseByClassId")
	@SystemControllerLog(description = "Ա��������־��Ϣ--����������--���ݿγ̲�ѯ��ʱ")
	public @ResponseBody ResultMessage getCourseByClassId(int classId) {
		ResultMessage rm = new ResultMessage();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		Map map = new HashMap();
		map.put("empId", emp.getId());
		if (classId != 0) {
			map.put("classId", classId);
		}
		List<EduCourseQuery> educourse = syllabusService.getCourse(map);// �õ�¼Ա�����̵Ŀγ�
		rm.setRows(educourse);
		rm.setTotal(educourse.size());
		return rm;
	}
}
