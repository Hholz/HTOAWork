package com.ht.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
/*
 * 获取当前登录用户的信息
 */
public class UserInfoUtil {

	public static Emp getEmp(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null == userInfo) {
			return null;
		}
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			
		}
		return emp;
		
	}
	public static Student getStudent(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null == userInfo) {
			return null;
		}
		Student student = null;
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
			
		}
		return student;
	}
}
