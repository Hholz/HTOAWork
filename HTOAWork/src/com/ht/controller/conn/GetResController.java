package com.ht.controller.conn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.popj.student.Student;
import com.ht.popj.sysSet.Residence;
import com.ht.service.sysSet.ResidenceService;

@Controller
@RequestMapping("/conn")
public class GetResController {

	@Autowired
	ResidenceService residenceService;
	@RequestMapping("/AllRes")
	public @ResponseBody List<Residence> AllRes(){
		List<Residence> resList = new ArrayList<Residence>();
		resList = residenceService.selectAll();
		return resList;
	}
}