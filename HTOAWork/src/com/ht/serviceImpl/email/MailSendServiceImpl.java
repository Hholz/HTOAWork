package com.ht.serviceImpl.email;

import com.ht.service.email.MailSendService;
import com.ht.util.email.MailSenderInfo;
import com.ht.util.email.SimpleMailSender;

public class MailSendServiceImpl implements MailSendService{

	 /**
     * �����ʼ�
     * @param toAddress
     * @param subject
     * @param content
     */
	@Override
	public void sendMail(String[] toAddress, String subject, String content) {
		 MailSenderInfo mailInfo =  new MailSenderInfo();
		  mailInfo.setMailServerHost("smtp.qq.com");
		  mailInfo.setMailServerPort("25");
		  mailInfo.setValidate(true);
		  
		  // �����û���
		  mailInfo.setUserName("");
		  // ��������
		  mailInfo.setPassword("");
		  // ����������
		  mailInfo.setFromAddress("");
		  // �ռ�������

		  mailInfo.setToAddress(toAddress);
		  // �ʼ�����
		  mailInfo.setSubject(subject);
		  // �ʼ�����
		  mailInfo.setContent(content);
		  SimpleMailSender.sendHtmlMail(mailInfo);
		
		
	}

}
