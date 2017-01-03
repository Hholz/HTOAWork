package com.ht.controller.student;

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

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.popj.student.StudentJobs;
import com.ht.service.student.StuJobService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.util.RandomGet;
import com.ht.util.RemarkSet;

@Controller
@RequestMapping("/student/stujob")
public class StudentJobController {

	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	StudentService studentService;
	@Autowired
	StuJobService stuJobService;
	
	/*
	 * ͨ���ӿ�����ȡ���еĽ����Ϣ��������а༶List
	 */
	@RequestMapping("/page")
	@SystemControllerLog(description = "����ѧ����ҵ����ҳ��")
	public String page(Model model) throws Exception{  
		List<StudentFall> fallList = new ArrayList<StudentFall>();
		fallList = studentInfoService.selectStufallclasstwo();
		model.addAttribute("fallList", fallList);
		return "student/jobManage";
	}
	
	
	/*
	 * ͨ���༶id����ȡ���е�ѧ����ϢstuList���ŵ�ҳ��
	 */
	@RequestMapping(value = "/ifram", method = { RequestMethod.GET })
	@SystemControllerLog(description = "��ȡѧ����ҵ�����Ҳ�iframҳ��")
	public String iframPage(){ 
		return "student/testIfram";
	}
	
	
	/*
	 * ͨ���༶id����ȡ���е�ѧ����ϢstuList���ŵ�ҳ��
	 */
	@RequestMapping(value = "/ifram/{id}", method = { RequestMethod.GET })
	@SystemControllerLog(description = "ͨ�����id����̬��ȡiframҳ��")
	public String ifram(Model model,@PathVariable("id")Integer id){ 
		//�༶��Ϣ�������н��Ķ���
		StudentClass stuCls = new StudentClass();
		if(studentInfoService.selectById(id)!=null){
			stuCls = studentInfoService.selectById(id);
		}
		List<Student> stuList = new ArrayList<Student>();
		Student student = new Student();
		student.setClassid(id);
		stuList = studentService.selectByDynamic(student);
		model.addAttribute("stuList", stuList);
		model.addAttribute("stuCls", stuCls);
		return "student/testIfram";
	}
	
	
	/*
	 * ͨ���༶id����ȡ���е�ѧ����ϢstuList���ŵ�ҳ��
	 */
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@SystemControllerLog(description = "ͨ��ѧ��id��ȡ��ҵ��ϢJSON")
	public @ResponseBody StudentJobs getStuJob(Model model,@PathVariable("id")Integer id){ 
		StudentJobs stuJob = stuJobService.selectByStuId(id);
		return stuJob;
	}
	
	
	
	@RequestMapping("/addOrUp")
	@SystemControllerLog(description = "ѧ����ҵ��Ϣ�������޸�")
	public @ResponseBody int addOrUpdate(StudentJobs studentJobs) throws Exception{  
		if(null!=studentJobs){
			if(studentJobs.getId()==null||studentJobs.getId()==0){
				//����ʱ��
				studentJobs.setCreateTime(new Date());
				studentJobs.setRemark(RemarkSet.getRemark("���"));
				int count = stuJobService.insertByPJ(studentJobs);
				return count;
			}else{
				//����ʱ��
				studentJobs.setUpdateTime(new Date());
				studentJobs.setRemark(RemarkSet.getRemark("�޸�"));
				int count = stuJobService.updateByPJ(studentJobs);
				return count;
			}
		}
		return 0;
	}
	
}
