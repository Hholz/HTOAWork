package com.ht.controller.student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.ht.popj.education.EduMajor;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.education.MajorService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

import common.Logger;
/*
 * �༶����ҳ�棨�쵼������
 */
@Controller
@RequestMapping("/student/class")
public class StudentClassController {

	Logger logger = Logger.getLogger(StudentClassController.class);
	
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	EmpService empservice;
	//רҵ��service
	@Autowired
	MajorService majorService;
	
	//����༶����ҳ��
	@RequestMapping("/page")
	public String studentListclass(Model model,StudentClass sc,Emp emp) throws Exception{
		List<StudentFall> sList = studentInfoService.selectStudentFall();
		List<StudentClass> clist = studentInfoService.selectStudentclass(sc);
		List<Emp> empList = empservice.selectEmp(emp);
		List<EduMajor> majorList = majorService.selectMajorAll();
		model.addAttribute("sList", sList);
		model.addAttribute("clist", clist);
		model.addAttribute("empList", empList);
		model.addAttribute("majorList", majorList);
		return "student/StudentClassinfo";
	}
	//���༶�Ƿ�������
	@RequestMapping("/check/classname")
	public @ResponseBody boolean studentListclass(Model model,StudentClass studentClass,String classname) throws Exception{
		List<StudentClass> list = studentInfoService.selectStudentclass(studentClass);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@RequestMapping("/classlistJson")
	@SystemControllerLog(description = "�༶��Ϣcontroller���list��")
	public @ResponseBody ResultMessage classlistJson(int limit, int offset,Model model,StudentClass student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<StudentClass> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = studentInfoService.selectStudentclass(student);
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentClass> pageInfo = new PageInfo<StudentClass>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//�����༶
	@RequestMapping("/add")
	public @ResponseBody int addStudentclass(Model model,StudentClass studentClass,int no,boolean testCls) throws Exception{
		//studentClass.setClassno(st);
		if(null!=studentClass){
			StudentClass cls = new StudentClass();
			cls.setClassname(studentClass.getClassname());
			List<StudentClass> list = studentInfoService.selectStudentclass(cls);
			if(list.size()>0){
				logger.error("�ð����Ѿ�����");
				return -1;
			}
			
			if(null==studentClass.getCount()){
				logger.error("û�����ð༶����");
				return 0;
			}
			if(null==studentClass.getLevelId()||studentClass.getLevelId().isEmpty()){
				logger.error("û�����ý��id");
				return 0;
			}
			StudentFall fall = studentInfoService.selectByPrimaryKeyOfFall(Integer.parseInt(studentClass.getLevelId()));
			//String classno = "2106SC01";
			String leave = fall.getLevel().substring(0, 4);
			EduMajor eduMajor = majorService.selectByPrimaryKey(studentClass.getMajorId());
			String major = eduMajor.getCode();
			String num = String.format("%02d",no);
			String classno = leave+major+num;
			logger.info("�����༶���༶��ţ�"+classno);
			if(testCls){
				studentClass.setClsStatus(-1);//��ѧ��
			}else{
				studentClass.setClsStatus(0);//�°�
			}
			studentClass.setClassno(classno);
			studentClass.setCreateTime(new Date());
			return studentInfoService.insertSelective(studentClass);
		}
		return 0;
	}
	//�޸İ༶��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentclass(Model model,StudentClass cls,boolean testCls){
		if(null!=cls && null!=cls.getId()){
			if(null==cls.getCount()){
				return 0;
			}else if(cls.getCount()<studentInfoService.countById(cls.getId())){
				return -1;//�༶�������õı����������٣��޸�ʧ��
			}
			if(testCls){
				cls.setClsStatus(-1);//��ѧ��
			}else{
				cls.setClsStatus(0);//�°�
			}
			return studentInfoService.updateByPrimaryKeySelective(cls);
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//ɾ���༶��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentclass(Model model,StudentClass cls){  
		if(null!=cls){
			if(null==cls.getId()){
				logger.error("δ����༶id���޷�����ɾ������");
				return 0;
			}else if(studentInfoService.countById(cls.getId())>0){
				logger.error("�ð��Ѿ�����ѧ�����޷�����ɾ������");
				return -1;
			}
			studentInfoService.updateByPrimaryKeySelective(cls);
			return 1;
		}
		return 0;
	}
}
