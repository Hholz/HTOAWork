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
	@SystemControllerLog(description = "����ѧ������ά������ҳ��")
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

	//������ѡ�༶������ѧ����Json����
	@RequestMapping("findstudent")
	@SystemControllerLog(description = "������ѡ�༶������ѧ����Json����")
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
	@SystemControllerLog(description = "ѧ�����Թ���controller���list��")
	public @ResponseBody ResultMessage StudentComputerList(int limit, int offset, Model model,
			StudentComputerApply student) throws Exception {
		ResultMessage rm = new ResultMessage();
		List<StudentComputerApply> sList = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if (student != null) {
			sList = studentComputerService.studentcomputermanage(student);
		}
		System.out.println(sList.size());
		// ȡ��ҳ��Ϣ
		PageInfo<StudentComputerApply> pageInfo = new PageInfo<StudentComputerApply>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	// �������Թ������
	@RequestMapping("/add")
	@SystemControllerLog(description = "����ѧ������ά������")
	public @ResponseBody int addStudentComputer(Model model, StudentComputerApply stuconputer) throws Exception {
		stuconputer.setFlowid(31);
		stuconputer.setManagetype(4);
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		
		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
		}
		stuconputer.setCreateman(e.getId());
		int i = studentComputerService.insertSelective(stuconputer);

		return i;
	}

	// ���µ��Թ������
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸�ѧ������ά������")
	public @ResponseBody int updateStudentComputer(Model model, StudentComputerApply student) {
		if (null != student) {
			if (null == student.getManagetype() || student.getManagetype()==0) {
				// ����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			if (null == student.getRemark() || student.getRemark().isEmpty()) {
				// ����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			studentComputerService.updateByPrimaryKeySelective(student);
			System.out.println("pppp000000000ooo");
			return 1;
		}
		return 0;
	}

	// ɾ�����Թ������
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ��ѧ������ά������")
	public @ResponseBody int deleteStudentComputer(Model model, StudentComputerApply student) {
		if (null != student) {
			if (null == student.getId()) {
				// ����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			studentComputerService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}

	@RequestMapping(value = "upset")
	@SystemControllerLog(description = "�ύ����ά��������Ϣ")
	public @ResponseBody int upset(Model model, StudentComputerApply applymaterial) {
		// �������ύ�Ĺ黹��������״̬Ϊ1
		applymaterial.setStat(1);

		// ���ɵ���
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(d);
		System.out.println(str);
		str = str.substring(0, 8);
		str = str + RandomGet.getSix() + "DNWXD";
		// ���õ���
		applymaterial.setApplyno(str);

		FlowModel f = new FlowModel();
		// ���̽���
		Flowschedule fsch = new Flowschedule();
		// �칫��Ʒ�깺�������idΪ1
		f.setFlowmodeltypeid(13);
		// ��ѯ�칫��Ʒ�깺������������
		List<FlowModel> flist = fmservice.findList(f);
		for (int i = 0; i < flist.size(); i++) {
			f = flist.get(i);
			fsch.setApplynum(str);
			// ��������δ���
			fsch.setFlowstatus(0);
			// ����ʱ,idΪ��
			fsch.setId(null);

			fsch.setFlowtype(f.getFlowmodeltypeid());
			fsch.setFlowdot(f.getId());
			fscheduleservice.insertSelective(fsch);
		}

		studentComputerService.updateByPrimaryKeySelective(applymaterial);
		return 1;
	}
}
