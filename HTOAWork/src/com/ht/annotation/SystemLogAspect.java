package com.ht.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * �е���
 * 
 * @author tiangai
 * @since 2014-08-05 Pm 20:35
 * @version 1.0
 */
@Aspect
@Component
public class SystemLogAspect {
	// ע��Service���ڰ���־�������ݿ�
	// �����쳣��־��¼����
	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	// Service���е�
	@Pointcut("@annotation(com.ht.annotation.SystemServiceLog)")
	public void serviceAspect() {
		logger.info("Service���е�");
	}

	// Controller��annotation�е�
	@Pointcut("@annotation(com.ht.annotation.SystemControllerLog)")
	public void controllerAspect() {
		logger.info("Controller���е�");
	}

	/**
	 * ǰ��֪ͨ ��������Controller���¼�û��Ĳ���
	 * 
	 * @param joinPoint
	 *            �е�
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// ��ȡsession�е��û�
		// �����IP
		String ip = request.getRemoteAddr();
		try {
			// *========����̨���=========*//
			logger.info("=====ǰ��֪ͨ��ʼ=====");
			logger.info("���󷽷�:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			logger.info("��������:" + getControllerMethodDescription(joinPoint));
			logger.info("����IP:" + ip);
			// *========���ݿ���־=========*//
			// �������ݿ�
			logger.info("=====ǰ��֪ͨ����=====");
		} catch (Exception e) {
			// ��¼�����쳣��־
			logger.error("==ǰ��֪ͨ�쳣==");
			logger.error("�쳣��Ϣ:{}", e.getMessage());
		}
	}
	@After("serviceAspect()")
	public void doAfter(JoinPoint joinPoint) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// ��ȡsession�е��û�
		// �����IP
		String ip = request.getRemoteAddr();
		try {
			// *========����̨���=========*//
			logger.info("=====ǰ��֪ͨ��ʼ=====");
			logger.info("���󷽷�:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			logger.info("��������:" + getControllerMethodDescription(joinPoint));
			logger.info("����IP:" + ip);
			// *========���ݿ���־=========*//
			// �������ݿ�
			logger.info("=====ǰ��֪ͨ����=====");
		} catch (Exception e) {
			// ��¼�����쳣��־
			logger.error("==ǰ��֪ͨ�쳣==");
			logger.error("�쳣��Ϣ:{}", e.getMessage());
		}
	}

	/**
	 * �쳣֪ͨ ��������service���¼�쳣��־
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// ��ȡsession�е��û�
		// ��ȡ����ip
		String ip = request.getRemoteAddr();
		// ��ȡ�û����󷽷��Ĳ��������л�ΪJSON��ʽ�ַ���
		try {
			/* ========����̨���========= */
			logger.info("=====�쳣֪ͨ��ʼ=====");
			logger.info("�쳣����:" + e.getClass().getName());
			logger.info("�쳣��Ϣ:" + e.getMessage());
			logger.info("�쳣����:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			logger.info("��������:" + getServiceMthodDescription(joinPoint));
			logger.info("����IP:" + ip);
			/* ==========���ݿ���־========= */
			logger.info("=====�쳣֪ͨ����=====");
		} catch (Exception ex) {
			// ��¼�����쳣��־
			logger.error("==�쳣֪ͨ�쳣==");
			logger.error("�쳣��Ϣ:{}", ex.getMessage());
		}
		/* ==========��¼�����쳣��־========== */

	}

	/**
	 * ��ȡע���жԷ�����������Ϣ ����service��ע��
	 * 
	 * @param joinPoint
	 *            �е�
	 * @return ��������
	 * @throws Exception
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemServiceLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * ��ȡע���жԷ�����������Ϣ ����Controller��ע��
	 * 
	 * @param joinPoint
	 *            �е�
	 * @return ��������
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}
}