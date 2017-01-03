package com.ht.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
/*
 * 该类未使用
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter{
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {//判断请求是否是登录请求
            if (isLoginSubmission(request, response)) { //判断请求是否是post方法
                return executeLogin(request, response);//执行登录验证
            } else {//如果是get方法则会返回true,跳转到登陆页面
                return true;
            }
        } else {//如果访问的是非登录页面，则跳转到登录页面
        	WebUtils.issueRedirect(request, response, "/page/lockscreen.html");//跳转页面
            //saveRequestAndRedirectToLogin(request, response);
            return false;
        }
	}
}
