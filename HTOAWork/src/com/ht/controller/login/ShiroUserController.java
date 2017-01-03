package com.ht.controller.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.popj.login.ShiroRole;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserRole;
import com.ht.service.login.ShiroUserRoleService;
import com.ht.service.login.ShiroUserService;
import com.ht.util.RandomGet;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/user")
public class ShiroUserController {


	@Autowired
	ShiroUserService shiroUserService;
	@Autowired
	ShiroUserRoleService shiroUserRoleService;
	@RequestMapping("/page")
	public String page(){  
		return "power/userRole";
	}
	/*
	 * �����û�
	 */
	@RequestMapping("/userListJson")
	public @ResponseBody ResultMessage userListJson(Integer limit, Integer offset){  
		ResultMessage rm = new ResultMessage();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		List<ShiroUser> userList = new ArrayList<ShiroUser>();
		userList = shiroUserService.selectAll();
        PageInfo<ShiroUser> pageInfo = new PageInfo<ShiroUser>(userList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(userList);
		return rm;
	}
	/*
	 * Ϊ�û���ӽ�ɫ
	 */
	@RequestMapping(value = "/addRole/{userId}/{roleId}")
	public @ResponseBody int addRole(@PathVariable("userId")Integer userId,@PathVariable("roleId")Integer roleId){  
		ShiroUserRole userRole = new ShiroUserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		userRole.setCreateTime(new Date());
		userRole.setRemark(RemarkSet.getRemark("���"));
		int count = shiroUserRoleService.insertByPJ(userRole);
		return count;
	}
	/*
	 * Ϊ�û�ɾ����ɫ
	 */
	@RequestMapping(value = "/delRole/{userId}/{roleId}")
	public @ResponseBody int delRole(@PathVariable("userId")Integer userId,@PathVariable("roleId")Integer roleId){  
		int count = shiroUserRoleService.delBy2Id(userId,roleId);
		return count;
	}
	/*
	 * Ϊ�û���ʼ������
	 */
	@RequestMapping(value = "/respwd/{id}")
	public @ResponseBody int delRole(@PathVariable("id")Integer userId){
		ShiroUser user = new ShiroUser();
		user.setId(userId);
		user.setPwd(Base64.encodeToString("123456".getBytes()));
		int count = shiroUserService.updateByPJ(user);
		return count;
	}
}
