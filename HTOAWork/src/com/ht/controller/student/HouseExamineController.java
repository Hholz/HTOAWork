package com.ht.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/*
 * Àﬁ…·ºÏ≤È
 */
@Controller
@RequestMapping("/student/houseExamine")
public class HouseExamineController {

	@RequestMapping("page")
	public String page(){  
		return "student/houseExamine";
	}	
}
