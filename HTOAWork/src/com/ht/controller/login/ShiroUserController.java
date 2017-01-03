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
	 * 所有用户
	 */
	@RequestMapping("/userListJson")
	public @ResponseBody ResultMessage userListJson(Integer limit, Integer offset){  
		ResultMessage rm = new ResultMessage();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		List<ShiroUser> userList = new ArrayList<ShiroUser>();
		userList = shiroUserService.selectAll();
        PageInfo<ShiroUser> pageInfo = new PageInfo<ShiroUser>(userList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(userList);
		return rm;
	}
	/*
	 * 为用户添加角色
	 */
	@RequestMapping(value = "/addRole/{userId}/{roleId}")
	public @ResponseBody int addRole(@PathVariable("userId")Integer userId,@PathVariable("roleId")Integer roleId){  
		ShiroUserRole userRole = new ShiroUserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		userRole.setCreateTime(new Date());
		userRole.setRemark(RemarkSet.getRemark("添加"));
		int count = shiroUserRoleService.insertByPJ(userRole);
		return count;
	}
	/*
	 * 为用户删除角色
	 */
	@RequestMapping(value = "/delRole/{userId}/{roleId}")
	public @ResponseBody int delRole(@PathVariable("userId")Integer userId,@PathVariable("roleId")Integer roleId){  
		int count = shiroUserRoleService.delBy2Id(userId,roleId);
		return count;
	}
	/*
	 * 为用户初始化密码
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
