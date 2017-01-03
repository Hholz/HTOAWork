package com.ht.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.service.student.StudentLayerService;


@Controller
@RequestMapping("student/Layer")
public class StudentLayerController {

	@Autowired
	StudentLayerService studentLayerService;
	
	//ɾ��¥�㣬ʵ�������޸�״̬����
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentLayer(Model model,@PathVariable("id")Integer id){
		//ʵ�����޸�״̬�������޸ķ���
		int count = studentLayerService.updateLayerStatusByPrimaryKey(id);
		return count;
	}
}
