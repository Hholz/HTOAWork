package com.ht.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
/*
 * ����δʹ��
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter{
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {//�ж������Ƿ��ǵ�¼����
            if (isLoginSubmission(request, response)) { //�ж������Ƿ���post����
                return executeLogin(request, response);//ִ�е�¼��֤
            } else {//�����get������᷵��true,��ת����½ҳ��
                return true;
            }
        } else {//������ʵ��Ƿǵ�¼ҳ�棬����ת����¼ҳ��
        	WebUtils.issueRedirect(request, response, "/page/lockscreen.html");//��תҳ��
            //saveRequestAndRedirectToLogin(request, response);
            return false;
        }
	}
}
