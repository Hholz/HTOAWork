package com.ht.controller.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/*
 * 用户注册
 */
import org.springframework.web.bind.annotation.RequestMapping;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.login.ShiroUserRole;
import com.ht.popj.student.Student;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.login.ShiroUserInfoService;
import com.ht.service.login.ShiroUserRoleService;
import com.ht.service.login.ShiroUserService;
import com.ht.service.student.StudentService;
@Controller
public class RegisterController {

	@Autowired
	StudentService studentService;
	
	@Autowired
	EmpService empService;
	
	@Autowired
	ShiroUserService userService;
	@Autowired
	ShiroUserRoleService userRoleService;
	@Autowired
	ShiroUserInfoService shiroUserInfoService;
	/*
	 * 进入学生 注册界面
	 */
	@RequestMapping("/register")
	public String register(){
		
		return "register";
	}
	/*
	 * 进入 员工注册界面
	 */
	@RequestMapping("/registerEmp")
	public String registerEmp(){
		
		return "register_emp";
	}
	/*
	 * 进行学生注册
	 */
	@RequestMapping("/toRegister")
	public String toRegister(String name,String phone,String password){
		//1.通过姓名和手机号去判断该学生是否操存在
		Student temp = new Student();
		temp.setStuname(name);
		temp.setPhone(phone);
		List<Student> stuList = new ArrayList<Student>();
		stuList = studentService.selectByDynamic(temp);
		//判断是否存在该学生
		if(null!=stuList&&stuList.size()==1){
			Student student = stuList.get(0);
			//添加用户表
			ShiroUser user = new ShiroUser();
			user.setName(student.getStuname());
			if(null!=student.getPhone()){
				user.setPhone(student.getPhone());
			}
			user.setPwd(Base64.encodeToString(password.getBytes()));
			user.setUserType(2);//2是学生
			int count2 =userService.insertByPJ(user);
			//添加用户信息表
			ShiroUserInfo userInfo = new ShiroUserInfo();
			userInfo.setUserId(user.getId());
			userInfo.setStuId(student.getId());
			int count3 =shiroUserInfoService.insertByPJ(userInfo);
			//给用户添加默认角色
			ShiroUserRole sur = new ShiroUserRole();
			sur.setUserId(user.getId());
			sur.setRoleId(2);//学生
			int count4 = userRoleService.insertByPJ(sur);
			if(count2>0&&count3>0&&count4>0){
				return "regSucc";
			}else{
				return "regFail";
			}
		}
		return "register";
	}
	/*
	 * 进行员工注册
	 */
	@RequestMapping("/toRegisterEmp")
	public String toRegisterEmp(String name,String phone,String password){
		//1.通过姓名和手机号去判断该学生是否操存在
		Emp temp = new Emp();
		temp.setEmpname(name);
		temp.setPhone(phone);
		List<Emp> empList = new ArrayList<Emp>();
		empList = empService.selectEmp(temp);
		//判断是否存在该学生
		if(null!=empList&&empList.size()==1){
			Emp emp = empList.get(0);
			//添加用户表
			ShiroUser user = new ShiroUser();
			user.setName(emp.getEmpname());
			if(null!=emp.getPhone()){
				user.setPhone(emp.getPhone());
			}
			user.setPwd(Base64.encodeToString(password.getBytes()));
			user.setUserType(3);//3是员工
			int count2 =userService.insertByPJ(user);
			//添加用户信息表
			ShiroUserInfo userInfo = new ShiroUserInfo();
			userInfo.setUserId(user.getId());
			userInfo.setEmpId(emp.getId());
			int count3 =shiroUserInfoService.insertByPJ(userInfo);
			//给用户添加默认角色
			ShiroUserRole sur = new ShiroUserRole();
			sur.setUserId(user.getId());
			sur.setRoleId(3);//员工
			int count4 = userRoleService.insertByPJ(sur);
			if(count2>0&&count3>0&&count4>0){
				return "regSucc";
			}else{
				return "regFail";
			}
		}
		return "register";
	}
}
