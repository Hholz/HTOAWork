package com.ht.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ht.annotation.SystemControllerLog;

/*
 * 获取要处理的分班申请
 */
@Controller
@RequestMapping("/get/ajc")
public class GetAjcController {

	/*
	 * 获取要处理的分班申请页面
	 */
	@RequestMapping("page")
	@SystemControllerLog(description = "进入处理分班申请页面")
	public String page(){  
		return "student/tecGetAjc";
	}	
}
