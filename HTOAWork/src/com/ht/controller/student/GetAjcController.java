package com.ht.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ht.annotation.SystemControllerLog;

/*
 * ��ȡҪ����ķְ�����
 */
@Controller
@RequestMapping("/get/ajc")
public class GetAjcController {

	/*
	 * ��ȡҪ����ķְ�����ҳ��
	 */
	@RequestMapping("page")
	@SystemControllerLog(description = "���봦��ְ�����ҳ��")
	public String page(){  
		return "student/tecGetAjc";
	}	
}
