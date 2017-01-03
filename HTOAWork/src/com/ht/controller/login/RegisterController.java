package com.ht.controller.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/*
 * �û�ע��
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
	 * ����ѧ�� ע�����
	 */
	@RequestMapping("/register")
	public String register(){
		
		return "register";
	}
	/*
	 * ���� Ա��ע�����
	 */
	@RequestMapping("/registerEmp")
	public String registerEmp(){
		
		return "register_emp";
	}
	/*
	 * ����ѧ��ע��
	 */
	@RequestMapping("/toRegister")
	public String toRegister(String name,String phone,String password){
		//1.ͨ���������ֻ���ȥ�жϸ�ѧ���Ƿ�ٴ���
		Student temp = new Student();
		temp.setStuname(name);
		temp.setPhone(phone);
		List<Student> stuList = new ArrayList<Student>();
		stuList = studentService.selectByDynamic(temp);
		//�ж��Ƿ���ڸ�ѧ��
		if(null!=stuList&&stuList.size()==1){
			Student student = stuList.get(0);
			//����û���
			ShiroUser user = new ShiroUser();
			user.setName(student.getStuname());
			if(null!=student.getPhone()){
				user.setPhone(student.getPhone());
			}
			user.setPwd(Base64.encodeToString(password.getBytes()));
			user.setUserType(2);//2��ѧ��
			int count2 =userService.insertByPJ(user);
			//����û���Ϣ��
			ShiroUserInfo userInfo = new ShiroUserInfo();
			userInfo.setUserId(user.getId());
			userInfo.setStuId(student.getId());
			int count3 =shiroUserInfoService.insertByPJ(userInfo);
			//���û����Ĭ�Ͻ�ɫ
			ShiroUserRole sur = new ShiroUserRole();
			sur.setUserId(user.getId());
			sur.setRoleId(2);//ѧ��
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
	 * ����Ա��ע��
	 */
	@RequestMapping("/toRegisterEmp")
	public String toRegisterEmp(String name,String phone,String password){
		//1.ͨ���������ֻ���ȥ�жϸ�ѧ���Ƿ�ٴ���
		Emp temp = new Emp();
		temp.setEmpname(name);
		temp.setPhone(phone);
		List<Emp> empList = new ArrayList<Emp>();
		empList = empService.selectEmp(temp);
		//�ж��Ƿ���ڸ�ѧ��
		if(null!=empList&&empList.size()==1){
			Emp emp = empList.get(0);
			//����û���
			ShiroUser user = new ShiroUser();
			user.setName(emp.getEmpname());
			if(null!=emp.getPhone()){
				user.setPhone(emp.getPhone());
			}
			user.setPwd(Base64.encodeToString(password.getBytes()));
			user.setUserType(3);//3��Ա��
			int count2 =userService.insertByPJ(user);
			//����û���Ϣ��
			ShiroUserInfo userInfo = new ShiroUserInfo();
			userInfo.setUserId(user.getId());
			userInfo.setEmpId(emp.getId());
			int count3 =shiroUserInfoService.insertByPJ(userInfo);
			//���û����Ĭ�Ͻ�ɫ
			ShiroUserRole sur = new ShiroUserRole();
			sur.setUserId(user.getId());
			sur.setRoleId(3);//Ա��
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
