package com.ht.service.email;

import org.springframework.stereotype.Service;

public interface MailSendService {
	  /**
       * �����ʼ�
       * @param toAddress
       * @param subject
       * @param content
       */
	  public void sendMail(String [] toAddress, String subject,String content );
	  
}

