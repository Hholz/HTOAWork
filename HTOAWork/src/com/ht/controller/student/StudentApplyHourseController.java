package com.ht.controller.student;

import java.util.ArrayList;
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
import com.ht.popj.finance.FinaceFeedbackset;
import com.ht.popj.student.StuApplyHourse;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentLayer;
import com.ht.service.student.StuApplyHourseService;
import com.ht.service.student.StudentFloorService;
import com.ht.service.student.StudentLayerService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student/applyHourse")
public class StudentApplyHourseController {

	@Autowired
	StuApplyHourseService stuApplyHourseService;
	@Autowired
	StudentFloorService studentFloorService;
	@Autowired
	StudentLayerService studentLayerService;
	@RequestMapping("/studentApplyHourse")
	@SystemControllerLog(description = "������������ҳ��")
	public String studentApplyHourse(Model model) throws Exception{
		
		List<StudentFloor> studentfloorlist = studentFloorService.selectStudentFloorAll();
		List<StudentLayer> studentlayerlist = studentLayerService.selectStudentLayerAll();
		model.addAttribute("studentfloorlist", studentfloorlist);
		model.addAttribute("studentlayerlist", studentlayerlist);
		return "/student/studentApplyHourse";
	}
	@RequestMapping("/applyListJson")
	public @ResponseBody ResultMessage listJson(int limit, int offset,StuApplyHourse apply){
		ResultMessage rm = new ResultMessage();
		List<StuApplyHourse> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(apply!=null){
			sList = stuApplyHourseService.selectByDynamic(apply);
		}else{
			sList = stuApplyHourseService.selectStuApplyHourseAll();
		}
//		  ȡ��ҳ��Ϣ
        PageInfo<StuApplyHourse> pageInfo = new PageInfo<StuApplyHourse>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addStudent")
	public @ResponseBody int  addStudentapply(Model model, StuApplyHourse stu){
		if(null!=stu){
			if(null==stu.getApplyhourse()||stu.getApplyhourse().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			
			int count = stuApplyHourseService.insert(stu);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//����ѧ��
		@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
		public @ResponseBody int updateApply(Model model,StuApplyHourse student) throws Exception{  
		    
			if(null!=student){
				int count = stuApplyHourseService.updateByPrimaryKeySelective(student);
				return count;
			}
			return 0;
		}
		
		//ɾ��ѧ������
		@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
		public @ResponseBody int deleteStudent(Model model,@PathVariable("id")Integer id){  
			int count = stuApplyHourseService.deleteByPrimaryKey(id);
			return count;
		}
}
