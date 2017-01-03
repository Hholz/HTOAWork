package com.ht.controller.conn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.student.StudentInfoService;

@Controller
@RequestMapping("/conn")
public class GetClsController {

	@Autowired
	StudentInfoService studentInfoService;
	//获取所有界别
	@RequestMapping("/AllFall")
	public @ResponseBody List<StudentFall> AllFall() throws Exception{  
		List<StudentFall> fallList = new ArrayList<StudentFall>();
		fallList = studentInfoService.selectStudentFall();
		return fallList;
	}
	//通过界别id来获取所有班级
	@RequestMapping(value = "/AllCls/{id}")
	public @ResponseBody List<StudentClass> AllCls(@PathVariable("id")Integer id) throws Exception{  
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		clsList = studentInfoService.selectByLevelId(id.toString());
		return clsList;
	}
	//通过界别id来获取所有班级
	@RequestMapping(value = "/clsInfo/{id}")
	public @ResponseBody StudentClass clsInfo(@PathVariable("id")Integer id) throws Exception{  
		StudentClass cls = studentInfoService.selectById(id);
		return cls;
	}
}
