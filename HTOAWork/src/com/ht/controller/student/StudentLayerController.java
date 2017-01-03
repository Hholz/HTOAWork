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
	
	//删除楼层，实际上是修改状态操作
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentLayer(Model model,@PathVariable("id")Integer id){
		//实际是修改状态，调用修改方法
		int count = studentLayerService.updateLayerStatusByPrimaryKey(id);
		return count;
	}
}
