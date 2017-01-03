package com.ht.controller.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.popj.student.Student;
import com.ht.popj.student.StudentRoom;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentRoomService;
import com.ht.service.student.StudentService;

@Controller
@RequestMapping("/student/studentRoom")
public class StudentRoomController {

	@Autowired StudentInfoService studentInfoService;
	@Autowired StudentRoomService studentRoomService;
	@Autowired StudentService studentService;
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int deleteStudentallot(Model model,StudentRoom room){
		//实际是修改状态，调用修改方法
		if(null!=room){
			Student stu = new Student();
			stu.setStuname(room.getStudentid());
			List<Student> list = studentService.selectByDynamic(stu);
			if(list.get(0).getStuStatus()==1){
				studentService.updateStuStatusById(room.getStudentid());
			}
			room.setStatus(0);
			int count = studentRoomService.updateByPrimaryKeySelective(room);
			return count;
		}
		//}else
		return 0;
	}
}
