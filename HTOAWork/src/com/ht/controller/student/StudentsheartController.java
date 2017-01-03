package com.ht.controller.student;

import java.util.ArrayList;
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
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.popj.student.StudentSayface;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentSayhService;
import com.ht.service.student.StudentService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/stuheart")
public class StudentsheartController {

	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	StudentSayhService studentsayh;
	@Autowired
	EmpService empservice;
	@Autowired
	StudentService studentService;
	
	//����̸��ҳ��
	@RequestMapping("/sheartStu")
	public String studentListfall(Model model,Student student,Emp emp,StudentClass stucls){
		System.out.println("=====");
		List<Emp> empList = empservice.selectEmp(emp);
		model.addAttribute("empList", empList);
		List<Student> list = studentService.selectStudentAll();
		model.addAttribute("stulist",list);
		List<StudentClass> scList = studentInfoService.selectStudentclass(stucls);
		model.addAttribute("scList",scList);
		return "student/Studentsayface";
	}
	
	@RequestMapping("/sayheart")
	@SystemControllerLog(description = "ѧ��̸��controller���list��")
	public @ResponseBody ResultMessage saystudentheart(int limit, int offset,Model model,StudentSayface student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<StudentSayface> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = studentsayh.studentsayheart(student);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentSayface> pageInfo = new PageInfo<StudentSayface>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//����ѧ��
	@RequestMapping("/heart/add")
	public @ResponseBody int addStudentsayface(Model model,StudentSayface student) throws Exception{ 
		if(null!=student){
			if(null==student.getSayproblem()||student.getSayproblem().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			int i =studentsayh.insertSelective(student);
			return i;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//����ѧ��
	@RequestMapping(value = "/heart/{id}", method = { RequestMethod.PUT })
//	@RequestMapping("/heartu")
	public @ResponseBody int updateStudentsayface(Model model,StudentSayface student){  
		if(null!=student){
			if(null==student.getSayscon()||student.getSayscon().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			if(null==student.getSayproblem()||student.getSayproblem().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			studentsayh.updateByPrimaryKeySelective(student);
			System.out.println("pppp000000000ooo");
			return 1;
		}
		return 0;
	}
	
	//ɾ��ѧ��
	@RequestMapping(value = "/heart/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentsayface(Model model,StudentSayface student){  
		if(null!=student){
			if(null==student.getId()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			studentsayh.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
	
	@RequestMapping("/findstudent")
	public @ResponseBody ResultMessage findemp(Model model,Student s) {
		ResultMessage rm = new ResultMessage();
		List<Student> sList = studentService.selectByDynamic(s);
		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
}
