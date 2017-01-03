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
 * ����ѧ������Ϣ
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
	@SystemControllerLog(description = "����ѧ����Ϣҳ��")
	public String stuList(Model model){
		//��ȡ��������
		List<Residence> resList = new ArrayList<Residence>();
		resList = residenceService.selectAll();
		//��ȡרҵ���
		List<EduMajor> majorList = new ArrayList<EduMajor>();
		majorList = majorService.selectMajorAll();
		//��ȡѧ�����
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
	//�˴�Ϊ��¼AOP����Controller��¼�û�����    
	@SystemControllerLog(description = "����ѧ����Ϣ��json����")
	public @ResponseBody ResultMessage listJson(Integer limit, Integer offset,Student student){
		ResultMessage rm = new ResultMessage();
		List<Student> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = studentService.selectByDynamic(student);
		}else{
			sList = studentService.selectStudentAll();
		}
		 // ȡ��ҳ��Ϣ
        PageInfo<Student> pageInfo = new PageInfo<Student>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	
	@RequestMapping("/addStudent")
	@SystemControllerLog(description = "ѧ������")
	public @ResponseBody int addStudent(Model model,Student student,Integer clsId) throws ParseException{ 
		//����ʱ��
		student.setCreateTime(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		student.setPassword("123456");
		student.setRemark(RemarkSet.getRemark("���ѧ��"));
		//ѧ��
		if(null!=clsId&&clsId!=0){
			StudentClass cls = studentInfoService.selectById(clsId);
			String classno = cls.getClassno();
			int no = studentInfoService.findBigCode(classno);//����һ���༶�����ѧ��
			String stuno = classno + String.format("%02d",no+1);
			student.setStuno(stuno);
		}
		if(null!=student){
			int count = studentService.addStudent(student);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	
	//����ѧ��
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "ѧ���޸�")
	public @ResponseBody int updateStudent(Model model,Student student,String birDate,String entDate,String oldStuno) throws ParseException{  
	    student.setRemark(RemarkSet.getRemark("����ѧ��"));
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
				logger.info("�޸�ѧ����"+student.getStuname()+",ѧ�ţ�"+student.getStuno());
			}else{
				logger.error("�޸�ѧ��"+student.getStuname()+"ʧ��");
			}
			return count;
		}
		logger.error("δ����student��Ϣ������ʧ��");
		return 0;
	}
	
	
	//ɾ��ѧ��
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ѧ��ɾ��")
	public @ResponseBody int deleteStudent(Model model,@PathVariable("id")Integer id){  
		int count = studentService.upStaById(id);
		return count;
	}
	
	
	//ѧ����ϸ��Ϣ
	@RequestMapping(value = "info/{id}")
	@SystemControllerLog(description = "����ѧ����ϸ��Ϣҳ��")
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
		//ѧ�����
		String classno = cls.getClassno();
		int no = studentInfoService.findBigCode(classno);//����һ���༶�����ѧ��
		String stuno = classno + String.format("%02d",no+1);
		return stuno;
	}
	
	@RequestMapping("/findStuByNo")
	public @ResponseBody Student  findStuByNo(Model model,String stuno){
		Student studnet = studentService.findStuByNo(stuno);
		return studnet;
	}
}
