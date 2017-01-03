package com.ht.serviceImpl.email;

import com.ht.service.email.MailSendService;
import com.ht.util.email.MailSenderInfo;
import com.ht.util.email.SimpleMailSender;

public class MailSendServiceImpl implements MailSendService{

	 /**
     * 发送邮件
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
		  
		  // 邮箱用户名
		  mailInfo.setUserName("");
		  // 邮箱密码
		  mailInfo.setPassword("");
		  // 发件人邮箱
		  mailInfo.setFromAddress("");
		  // 收件人邮箱

		  mailInfo.setToAddress(toAddress);
		  // 邮件标题
		  mailInfo.setSubject(subject);
		  // 邮件内容
		  mailInfo.setContent(content);
		  SimpleMailSender.sendHtmlMail(mailInfo);
		
		
	}

}
