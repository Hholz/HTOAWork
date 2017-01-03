package com.ht.controller.login;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.popj.login.ShiroAuth;
import com.ht.popj.login.ShiroAuthType;
import com.ht.popj.login.ShiroRole;
import com.ht.popj.login.ShiroRoleAuth;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.login.ShiroUserRole;
import com.ht.popj.student.Student;
import com.ht.service.login.ShiroAuthService;
import com.ht.service.login.ShiroRoleService;
import com.ht.service.login.ShiroUserService;
import com.ht.util.RandomGet;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/power")
public class ShiroPowerConterller {

	@Autowired
	ShiroRoleService shiroRoleService;
	@Autowired
	ShiroAuthService shiroAuthService;
	@RequestMapping("/page")
	public String page(){  
		return "power";
	}
	@RequestMapping("/roleAuth")
	public String roleAuth(){  
		return "power/roleAuth";
	}
	/*
	 * 所有角色
	 */
	@RequestMapping("/roleListJson")
	public @ResponseBody ResultMessage roleListJson(Integer limit, Integer offset){  
		ResultMessage rm = new ResultMessage();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		List<ShiroRole> roleList = new ArrayList<ShiroRole>();
		roleList = shiroRoleService.selectAll();
        PageInfo<ShiroRole> pageInfo = new PageInfo<ShiroRole>(roleList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(roleList);
		return rm;
	}
	/*
	 * 所有角色
	 */
	@RequestMapping(value = "/roleListJson/{id}/not")
	public @ResponseBody ResultMessage roleListJsonNot(@PathVariable("id")Integer id,Integer limit, Integer offset){  
		ResultMessage rm = new ResultMessage();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		List<ShiroRole> roleList = new ArrayList<ShiroRole>();
		List<ShiroRole> roleListHas = new ArrayList<ShiroRole>();
		roleList = shiroRoleService.selectAll();
		roleListHas = shiroRoleService.selectListByUserId(id);
		for(int i=0;i<roleListHas.size();i++){
			int roleId = roleListHas.get(i).getId();
			for(int j=0;j<roleList.size();j++){
				if(roleId == roleList.get(j).getId()){
					roleList.remove(j);
				}
			}
		}
        PageInfo<ShiroRole> pageInfo = new PageInfo<ShiroRole>(roleList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(roleList);
		return rm;
	}
	/*
	 * 所有角色
	 */
	@RequestMapping(value = "/roleListJson/{id}/not2")
	public @ResponseBody List<ShiroRole> roleListJsonNot2(@PathVariable("id")Integer id){  
		List<ShiroRole> roleList = new ArrayList<ShiroRole>();
		List<ShiroRole> roleListHas = new ArrayList<ShiroRole>();
		roleList = shiroRoleService.selectAll();
		roleListHas = shiroRoleService.selectListByUserId(id);
		for(int i=0;i<roleListHas.size();i++){
			int roleId = roleListHas.get(i).getId();
			for(int j=0;j<roleList.size();j++){
				if(roleId == roleList.get(j).getId()){
					roleList.remove(j);
				}
			}
		}
		return roleList;
	}
	/*
	 * 某个用户的所有角色
	 */
	@RequestMapping(value = "/roleListJson/{id}")
	public @ResponseBody ResultMessage roleListJsonByUId(@PathVariable("id")Integer id,Integer limit, Integer offset){  
		ResultMessage rm = new ResultMessage();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		List<ShiroRole> roleList = new ArrayList<ShiroRole>();
		roleList = shiroRoleService.selectListByUserId(id);
        PageInfo<ShiroRole> pageInfo = new PageInfo<ShiroRole>(roleList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(roleList);
		return rm;
	}
	/*
	 * 所有权限分类
	 */
	@RequestMapping("/authTypeListJson")
	public @ResponseBody ResultMessage authTypeListJson(Integer limit, Integer offset){  
		ResultMessage rm = new ResultMessage();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		List<ShiroAuthType> authTypeList = new ArrayList<ShiroAuthType>();
		authTypeList = shiroAuthService.selectAllType();
        PageInfo<ShiroAuthType> pageInfo = new PageInfo<ShiroAuthType>(authTypeList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(authTypeList);
		return rm;
	}
	/*
	 * 通过角色id来查权限list
	 */
	@RequestMapping(value = "/authListByRoleId/{id}")
	public @ResponseBody List<ShiroAuth> authListByRoleId(@PathVariable("id")Integer id){  
		//保存权限名，给前台提供判断是否打勾
		List<ShiroAuth> authList = new ArrayList<ShiroAuth>();
		authList = shiroAuthService.selectByAuthId(id);
		return authList;
	}
	/*
	 * 所有权限
	 */
	@RequestMapping("/authListJson")
	public @ResponseBody ResultMessage authListJson(Integer limit, Integer offset){  
		ResultMessage rm = new ResultMessage();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		List<ShiroAuth> authList = new ArrayList<ShiroAuth>();
		authList = shiroAuthService.selectAll();
        PageInfo<ShiroAuth> pageInfo = new PageInfo<ShiroAuth>(authList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(authList);
		return rm;
	}
	/*
	 * 某个类别的权限
	 */
	@RequestMapping(value = "/authListJson/{id}")
	public @ResponseBody ResultMessage authListJsonByTypeId(@PathVariable("id")Integer id,Integer limit, Integer offset){  
		ResultMessage rm = new ResultMessage();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		List<ShiroAuth> authList = new ArrayList<ShiroAuth>();
		authList = shiroAuthService.selectByTypeId(id);
        PageInfo<ShiroAuth> pageInfo = new PageInfo<ShiroAuth>(authList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(authList);
		return rm;
	}
	
	/*
	 * 新增权限
	 */
	@RequestMapping("/addPower")
	public @ResponseBody int addPower(Integer roleId,Integer authId){ 
		ShiroRoleAuth roleAuth = new ShiroRoleAuth();
		roleAuth.setRoleId(roleId);
		roleAuth.setAuthId(authId);
		int count = shiroRoleService.insertRoleAuth(roleAuth);
		return count;
	}
	/*
	 * 删除权限
	 */
	@RequestMapping("/delPower")
	public @ResponseBody int delPower(Integer roleId,Integer authId){  
		int count = shiroRoleService.deleteBy2Id(roleId, authId);
		return count;
	}
	
	/*
	 * 新增角色
	 */
	@RequestMapping("/role/add")
	public @ResponseBody int addStudent(ShiroRole role) throws ParseException{ 
		//创建时间
		role.setCreateTime(new Date());// new Date()为获取当前系统时间
		role.setRemark(RemarkSet.getRemark("添加学生"));
		if(null!=role){
			return shiroRoleService.insertByPJ(role);
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	/*
	 * 修改角色
	 */
	@RequestMapping(value = "role/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudent(ShiroRole role) throws ParseException{  
		role.setRemark(RemarkSet.getRemark("更新学生"));
		role.setUpdateTime(new Date());
		if(null!=role){
			int count = shiroRoleService.updateByPJ(role);
			return count;
		}
		return 0;
	}
	/*
	 * 删除角色
	 */
	@RequestMapping(value = "role/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudent(@PathVariable("id")Integer id){  
		int count = shiroRoleService.deleteById(id);
		return count;
	}
	
}
