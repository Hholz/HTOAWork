package com.ht.controller.android;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.android.util.ResultMessage;
import com.ht.pojo.AStudent;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.service.login.ShiroUserInfoService;
import com.ht.service.login.ShiroUserService;

@Controller
@RequestMapping("android/login")
public class LoginAction {

	@Autowired
	ShiroUserService userService;
	@Autowired
	ShiroUserInfoService shiroUserInfoService;
	
	@RequestMapping("")
	public @ResponseBody ResultMessage login(String username,String password){
		String pwd = Base64.encodeToString((password.toString()).getBytes()); 
		ShiroUser user = userService.selectByLogin(username, pwd);
		Student student = null;
		Emp emp = null;
		
		ShiroUserInfo userInfo = null;
		if(null != user){
			userInfo = shiroUserInfoService.selectByUserId(user.getId());
		}
		if(null != userInfo){
			if (null != userInfo.getStudent()) {
				student = userInfo.getStudent();
				AStudent stu = new AStudent();
				if(null!=student.getId()){
					stu.setId(student.getId());
				}
				stu.setStuno(student.getStuno());
				stu.setStuname(student.getStuname());
				stu.setAge(student.getAge());
				stu.setPhone(student.getPhone());
				stu.setSex(student.getSex());
				if(null!=student.getClassInfo()){
					stu.setClassName(student.getClassInfo().getClassname());
				}
				if(null!=student.getHourse()){
					stu.setHourseName(student.getHourse().getHoursename());
				}
				
				ResultMessage rm = new ResultMessage();
				rm.setResultCode("200");
				rm.setResultMessage("µÇÂ¼³É¹¦£¡");
				rm.setContext(stu);
				return rm;
			}
//			if (null != userInfo.getEmp()) {
//				emp = userInfo.getEmp();
//				ResultMessage rm = new ResultMessage();
//				rm.setResultCode("200");
//				rm.setResultMessage("µÇÂ¼³É¹¦£¡");
//				rm.setContext(emp);
//				return rm;
//			}
		}
		
		ResultMessage rm = new ResultMessage();
		rm.setResultCode("201");
		rm.setResultMessage("µÇÂ¼Ê§°Ü£¡");
		return rm;
	}
}
