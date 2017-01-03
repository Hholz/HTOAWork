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
import com.ht.popj.education.EduDept;
import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduSeminar;
import com.ht.popj.student.StudentClass;
import com.ht.service.education.DeptService;
import com.ht.service.education.MajorService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/major")
public class MajorController {

	@Autowired
	MajorService majorService;
	
	@Autowired
	DeptService deptService;
	
	@Autowired
	StudentInfoService studentInfoService;
	
	@RequestMapping("majorIndex")
	@SystemControllerLog(description = "����רҵ��Ϣҳ��")
	public String deptIndex(Model model){
		List<EduDept> depList = deptService.selectByDynamic(null);
		model.addAttribute("depList", depList);
		return "education/major_Index";
	}
	
	@RequestMapping("/addMajor")
	@SystemControllerLog(description = "������һ��רҵ��Ϣ")
	public @ResponseBody int addMajor(Model model, EduMajor major){
		//��ȡϵͳ��ǰʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		major.setCreateTime(str);
		int count = majorService.insert(major);
		List<EduMajor> majorList = new ArrayList<EduMajor>();
		model.addAttribute("mList", majorList);
		return count;
	}
	@RequestMapping("majorList")
	@SystemControllerLog(description = "��ѯ��רҵ��Ϣ��(Json)")
	public @ResponseBody ResultMessage majorList(int limit, int offset, Model model,EduMajor major){
		ResultMessage rm = new ResultMessage();
		List<EduMajor> sList = new ArrayList<EduMajor>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		
		sList = majorService.selectByDynamic(major);
		// ȡ��ҳ��Ϣ
		PageInfo<EduMajor> pageInfo = new PageInfo<EduMajor>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���һ��רҵ��Ϣ")
	public @ResponseBody int updateMajor(Model model, EduMajor major){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		major.setUpdateTime(str);
		int count = majorService.updateByPrimaryKeySelective(major);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����һ��רҵ��Ϣ")
	public @ResponseBody int deleteMajor(@PathVariable("id")Integer id){  
		int count=0;
		StudentClass  cls = new StudentClass();
		cls.setMajorId(id);
		List<StudentClass> list = studentInfoService.selectStudentclass(cls);
		if(list.size()!=0){//�����ѯ���������ݣ�����ɾ��
			count = -1;
		}else{
			count = majorService.deleteByPrimaryKey(id);
		}
		return count;
	}
}
