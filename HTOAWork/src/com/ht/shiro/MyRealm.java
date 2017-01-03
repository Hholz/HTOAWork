package com.ht.shiro;


import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ht.popj.login.ShiroAuth;
import com.ht.popj.login.ShiroRole;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.service.login.ShiroUserInfoService;
import com.ht.service.login.ShiroUserService;
import com.ht.service.student.StudentService;
import com.ht.serviceImpl.login.ShiroUserInfoServiceImpl;


public class MyRealm extends AuthorizingRealm {

	@Autowired
	ShiroUserService userService;
	@Autowired
	StudentService studentService;
	@Autowired
	ShiroUserInfoService shiroUserInfoService;

	/**
	 * ��֤��ǰ��¼��Subject
	 * 
	 * @see ������:�����и÷����ĵ���ʱ��ΪLoginController.login()������ִ��Subject.login()ʱ
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// ��ȡ�����û��������������
		// ʵ�������authcToken�Ǵ�LoginController����currentUser.login(token)��������
		// ����token�����ö���һ����,��������org.apache.shiro.authc.UsernamePasswordToken@33799a1e
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		StringBuffer password = new StringBuffer();
		for(char s : token.getPassword()){
			password.append(s);
		}
		String pwd = Base64.encodeToString((password.toString()).getBytes()); 
		ShiroUser user = userService.selectByLogin(username, pwd);
		if (user != null) {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getId(), Base64.decodeToString(user.getPwd()),
					user.getName());
			//��session�ﱣ��ѧ����Ϣ
			this.setSession("currentUser", user);
			//ShiroUserInfo����Student��emp����Ϣ
			ShiroUserInfo userInfo = shiroUserInfoService.selectByUserId(user.getId());
			this.setSession("userInfo", userInfo);
			return authcInfo;
		} else {
			return null;
		}
	}

	/**
	 * Ϊ��ǰ��¼��Subject�����ɫ��Ȩ��
	 * 
	 * @see ������:�����и÷����ĵ���ʱ��Ϊ����Ȩ��Դ������ʱ
	 * @see ������:����ÿ�η�������Ȩ��Դʱ����ִ�и÷����е��߼�,�����������Ĭ�ϲ�δ����AuthorizationCache
	 * @see ���˸о���ʹ����Spring3.1��ʼ�ṩ��ConcurrentMapCache֧��,
	 *      ����������Ƿ�����AuthorizationCache
	 * @see ����˵��������ݿ��ȡȨ����Ϣʱ,��ȥ����Spring3.1�ṩ�Ļ���,����ʹ��Shior�ṩ��AuthorizationCache
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		int currentUserId = (int) super.getAvailablePrincipal(principals);
		List<String> roleList = new ArrayList<String>();
		List<String> authList = new ArrayList<String>();
		ShiroUser user = userService.selectById(currentUserId);
		if (null != user) {
			// ʵ����User�а������û���ɫ��ʵ������Ϣ
			if (null != user.getRoleList() && user.getRoleList().size() > 0) {
				// ��ȡ��ǰ��¼�û��Ľ�ɫ
				for (ShiroRole role : user.getRoleList()) {
					roleList.add(role.getName());
					// ʵ����Role�а����н�ɫȨ�޵�ʵ������Ϣ
					if (null != role.getAuthList() && role.getAuthList().size() > 0) {
						// ��ȡȨ��
						for (ShiroAuth auth : role.getAuthList()) {
							if (!StringUtils.isEmpty(auth.getName())) {
								authList.add(auth.getName());
							}
						}
					}
				}
			}
		} else {
			throw new AuthorizationException();
		}
		// Ϊ��ǰ�û����ý�ɫ��Ȩ��
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(authList);
		return simpleAuthorInfo;
	}

	/**
	 * ��һЩ���ݷŵ�ShiroSession��,�Ա��������ط�ʹ��
	 * 
	 * @see ����Controller,ʹ��ʱֱ����HttpSession.getAttribute(key)�Ϳ���ȡ��
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out.println("SessionĬ�ϳ�ʱʱ��Ϊ[" + session.getTimeout() + "]����");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}
