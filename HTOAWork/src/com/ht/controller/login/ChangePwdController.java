package com.ht.controller.login;


import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ht.popj.login.ShiroUser;
import com.ht.service.login.ShiroUserService;

@Controller
@RequestMapping("/changePwd")
public class ChangePwdController {
	
	@Autowired
	ShiroUserService shiroUserService;
	@RequestMapping("/page")
	public String page(){  
		return "changePwd";
	}
	/*	
	 * ÐÞ¸ÄÃÜÂë
	 */
	@RequestMapping("/change")
	public String changePwd(String phone,String oldPwd,String newPwd){
		int count = 0;
		oldPwd = Base64.encodeToString(oldPwd.getBytes());
		ShiroUser shiroUser = shiroUserService.selectByLogin(phone, oldPwd);
		if(null!=shiroUser){
			shiroUser.setPwd(Base64.encodeToString(newPwd.getBytes()));
			count = shiroUserService.updateByPJ(shiroUser);
		}
		if(count>0){
			return "changeSucc";
		}else{
			return "changeFail";
		}
	}
}
