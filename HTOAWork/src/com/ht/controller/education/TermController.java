package com.ht.controller.education;

import java.text.SimpleDateFormat;
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
import com.ht.popj.education.EduCourse;
import com.ht.popj.education.EduDept;
import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentFall;
import com.ht.service.education.MajorService;
import com.ht.service.education.TermService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("education/term")
public class TermController {


	@Autowired
	StudentInfoService studentInfoService;
	
	@Autowired
	MajorService majorService;
	
	@Autowired
	TermService termService;
	
	@RequestMapping("termIndex")
	@SystemControllerLog(description = "����ѧ����Ϣҳ��")
	public String termIndex(Model model) throws Exception{
		//��ѯ���
		List<StudentFall> fallList =studentInfoService.selectStudentFall(); 
		model.addAttribute("fallList", fallList);
		//��ѯרҵ
		List<EduMajor> majorList = majorService.selectMajorAll();
		model.addAttribute("majorList", majorList);
		return "education/term_Index";
	}
	
	@RequestMapping("addTerm")
	@SystemControllerLog(description = "������һ��ѧ����Ϣ")
	public @ResponseBody int addTerm(Model model,EduTerm term){
		//��ȡϵͳ��ǰʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		term.setCreateTime(str);
		term.setStatus(1);
		EduMajor major = majorService.selectByPrimaryKey(term.getMajor_id());
		StudentFall fa = studentInfoService.selectByPrimaryKeyOfFall(term.getFall_id());
		String number = fa.getLevel().substring(0, 4);
		//�и��ַ�������ȡǰ��λ����
		String code = "";
		if(term.getTermName().equals("��һѧ��")){
			code = number +""+ major.getCode() +""+"01";
		}else if(term.getTermName().equals("�ڶ�ѧ��")){
			code = number +""+ major.getCode() +""+"02";
		}else if(term.getTermName().equals("����ѧ��")){
			code = number +""+ major.getCode() +""+"03";
		}else if(term.getTermName().equals("����ѧ��")){
			code = number +""+ major.getCode() +""+"04";
		}else if(term.getTermName().equals("����ѧ��")){
			code = number +""+ major.getCode() +""+"05";
		}else if(term.getTermName().equals("����ѧ��")){
			code = number +""+ major.getCode() +""+"06";
		}
		term.setCode(code);
		List<EduTerm> list = termService.selectTermAll();
		int count = 0;
		String strin = "";
		String check = term.getFall_id()+""+term.getMajor_id()+""+term.getTermName();
		for(int i=0;i<list.size();i++){
			 strin= list.get(i).getFall_id()+""+list.get(i).getMajor_id()+""+list.get(i).getTermName();
			 if(check.equals(strin)){
				 return count=2;
			 }
		}
		count = termService.insert(term);
		return count;
		
	}
	
	@RequestMapping("termList")
	@SystemControllerLog(description = "��ѯѧ����Ϣ(Json)")
	public @ResponseBody ResultMessage termList(int limit, int offset, String departmentname, String statu,Model model,EduTerm term){
		ResultMessage rm = new ResultMessage();
		List<EduTerm> sList = new ArrayList<EduTerm>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(term!=null){
			sList = termService.selectByDynamic(term);
		}else{
			sList = termService.selectByDynamic(term);
		}
		// ȡ��ҳ��Ϣ
		PageInfo<EduTerm> pageInfo = new PageInfo<EduTerm>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���һ��ѧ����Ϣ")
	public @ResponseBody int updateTerm(Model model, EduTerm term){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		term.setUpdateTime(str);
		term.setStatus(1);
		EduMajor major = majorService.selectByPrimaryKey(term.getMajor_id());
		StudentFall fa = studentInfoService.selectByPrimaryKeyOfFall(term.getFall_id());
		String number = fa.getLevel().substring(0, 4);
		//�и��ַ�������ȡǰ��λ����
		String code = "";
		if(term.getTermName().equals("��һѧ��")){
			code = number +""+ major.getCode() +""+"01";
		}else if(term.getTermName().equals("�ڶ�ѧ��")){
			code = number +""+ major.getCode() +""+"02";
		}else if(term.getTermName().equals("����ѧ��")){
			code = number +""+ major.getCode() +""+"03";
		}else if(term.getTermName().equals("����ѧ��")){
			code = number +""+ major.getCode() +""+"04";
		}else if(term.getTermName().equals("����ѧ��")){
			code = number +""+ major.getCode() +""+"05";
		}else if(term.getTermName().equals("����ѧ��")){
			code = number +""+ major.getCode() +""+"06";
		}
		term.setCode(code);
		List<EduTerm> list = termService.selectTermAll();
		int count = 0;
		String strin = "";
		String check = term.getFall_id()+""+term.getMajor_id()+""+term.getTermName();
		for(int i=0;i<list.size();i++){
			 strin= list.get(i).getFall_id()+""+list.get(i).getMajor_id()+""+list.get(i).getTermName();
			 if(check.equals(strin)){
				 return count=2;
			 }
		}
		count = termService.updateByPrimaryKey(term);
		return count;
	}
	@SystemControllerLog(description = "ɾ����һ��ѧ����Ϣ")
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteTerm(Model model,@PathVariable("id")Integer id){ 
//		List<Student> list = studentInfoService.selectByDynamic(record)
//		if(list.size()){
//			
//		}
		int count =  termService.deleteByPrimaryKey(id);
		return count;
	}
	
	
}
