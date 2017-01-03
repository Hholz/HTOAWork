package com.ht.controller.student;

import java.text.ParseException;
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
import com.ht.popj.student.StudentEdu;
import com.ht.popj.student.StudentFamily;
import com.ht.service.student.StudentEduService;
import com.ht.service.student.StudentFamilyService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;
/*
 * 
 * ѧ���ܽ����ͼ�ͥ��Ա��Ϣ
 */
@Controller
@RequestMapping("/student/edufam")
public class StuEduAndFamController {

	@Autowired
	StudentEduService stuEduService;
	@Autowired
	StudentFamilyService stuFamService;
	@RequestMapping("/page")
	public String page(){
		return "student/stuEduFam";
	}
	@RequestMapping(value = "/page/{id}")
	@SystemControllerLog(description = "ͨ��ѧ��id�����ܽ����ͼ�ͥ��Ա��Ϣҳ��")
	public String page(Model model,@PathVariable("id")Integer id){
		model.addAttribute("studentid", id);
		return "student/stuEduFam2";
	}
	/*
	 * ѧ������������json����
	 */
	@RequestMapping("/stuEduListJson")
	@SystemControllerLog(description = "����ѧ������������json����")
	public @ResponseBody ResultMessage stuEduListJson(int limit, int offset,StudentEdu studentEdu){
		ResultMessage rm = new ResultMessage();
		List<StudentEdu> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(null != studentEdu.getStudentId()){
			sList = stuEduService.selectBystuId(studentEdu.getStudentId());
		}else{
			sList = stuEduService.selectAll();
		}
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentEdu> pageInfo = new PageInfo<StudentEdu>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * ���ѧ���������
	 */
	@RequestMapping("/addStuEdu")
	@SystemControllerLog(description = "ѧ���ܽ�����Ϣ����")
	public @ResponseBody int addStuEdu(StudentEdu studentEdu,String bdate,String edate) throws ParseException{ 
		studentEdu.setBegindate(bdate);
		studentEdu.setEnddate(edate);
		//����ʱ��
		studentEdu.setCreateTime(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		//�����������
		studentEdu.setRemark(RemarkSet.getRemark("���"));
		if(null!=studentEdu){
			int count = stuEduService.insertByPJ(studentEdu);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	/*
	 * �޸�ѧ���������
	 */
	@RequestMapping(value = "edu/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "ѧ���ܽ�����Ϣ�޸�")
	public @ResponseBody int updateStuEdu(StudentEdu studentEdu,String bdate,String edate) throws ParseException{  
		studentEdu.setBegindate(bdate);
		studentEdu.setEnddate(edate);
		studentEdu.setRemark(RemarkSet.getRemark("����"));
		studentEdu.setUpdateTime(new Date());
		if(null!=studentEdu){
			int count = stuEduService.updateByPJ(studentEdu);
			return count;
		}
		return 0;
	}
	/*
	 * ɾ��ѧ���������
	 */
	@RequestMapping(value = "edu/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ѧ���ܽ�����Ϣɾ��")
	public @ResponseBody int deleteStuEdu(Model model,@PathVariable("id")Integer id){  
		int count = stuEduService.delByUpdate(id);
		return count;
	}
	
	
	/*
	 * ѧ����ͥ������json����
	 */
	@RequestMapping("/stuFamListJson")
	@SystemControllerLog(description = "����ѧ����ͥ������json����")
	public @ResponseBody ResultMessage stuFamListJson(int limit, int offset,StudentFamily studentFam){
		ResultMessage rm = new ResultMessage();
		List<StudentFamily> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(null != studentFam.getStudentId()){
			sList = stuFamService.selectBystuId(studentFam.getStudentId());
		}else{
			sList = stuFamService.selectAll();
		}
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentFamily> pageInfo = new PageInfo<StudentFamily>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * ��Ӽ�ͥ��Ա��Ϣ
	 */
	@RequestMapping("/addStuFam")
	@SystemControllerLog(description = "ѧ����ͥ��Ա��Ϣ����")
	public @ResponseBody int addStuFam(StudentFamily  studentFam) throws ParseException{ 
		//����ʱ��
		studentFam.setCreateTime(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		//�����������
		studentFam.setRemark(RemarkSet.getRemark("���"));
		if(null!=studentFam){
			int count = stuFamService.insertByPJ(studentFam);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	/*
	 * �޸ļ�ͥ�������
	 */
	@RequestMapping(value = "fam/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "ѧ����ͥ��Ա��Ϣ�޸�")
	public @ResponseBody int updateStuFam(StudentFamily  studentFam) throws ParseException{  
		studentFam.setRemark(RemarkSet.getRemark("����"));
		studentFam.setUpdateTime(new Date());
		if(null!=studentFam){
			int count = stuFamService.updateByPJ(studentFam);
			return count;
		}
		return 0;
	}
	/*
	 * ɾ����ͥ�������
	 */
	@RequestMapping(value = "fam/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ѧ����ͥ��Ա��Ϣɾ��")
	public @ResponseBody int deleteStuFam(Model model,@PathVariable("id")Integer id){  
		int count = stuFamService.delByUpdate(id);
		return count;
	}
}
