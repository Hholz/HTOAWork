package com.ht.util.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * 发送邮件类
 * Created by ziyi on 15-5-25. 
 */
public class SimpleMailSender {

 private static Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);
 
 public boolean sendTextMail(MailSenderInfo mailInfo) {
  // 判断是否需要身份认证
  MyAuthenticator authenticator = null;
  Properties pro = mailInfo.getProperties();
  if (mailInfo.isValidate()) {
   // 如果需要身份认证，则创建一个密码验证器
   authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
  }
  // 根据邮件会话属性和密码验证器构造一个发送邮件的session
  Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
  try {
   // 根据session创建一个邮件消息
   Message mailMessage = new MimeMessage(sendMailSession);
   // 创建邮件发送者地址
   Address from = new InternetAddress(mailInfo.getFromAddress());
   // 设置邮件消息的发送者
   mailMessage.setFrom(from);
   // 创建邮件的接收者地址，并设置到邮件消息中
  // Address to = new InternetAddress(mailInfo.getToAddress());
   //mailMessage.setRecipient(Message.RecipientType.TO, to);
   // 创建邮件发送者地址
   // 设置邮件消息的发送者
   mailMessage.setFrom(from);
   // 创建邮件的接收者地址，并设置到邮件消息中（单发）
  // Address to = new InternetAddress(mailInfo.getToAddress());
   
   // 创建邮件的接收者地址，并设置到邮件消息中（群发）   
   String[] mailToAddress = mailInfo.getToAddress() ;

   int len = mailToAddress.length ;

   Address to[] = new InternetAddress[len] ;

   for(int i=0;i<len;i++){

   to[i] = new InternetAddress(mailToAddress[i]) ;

   }
   //群发
   mailMessage.setRecipients(Message.RecipientType.TO,to);
   
   // 设置邮件消息的主题
   mailMessage.setSubject(mailInfo.getSubject());
   // 设置邮件消息发送的时间
   mailMessage.setSentDate(new Date());
   // 设置邮件消息的主要内容
   String mailContent = mailInfo.getContent();
   mailMessage.setText(mailContent);
   // 发送邮件
   Transport.send(mailMessage);
   return true;
  } catch (MessagingException ex) {
    logger.warn("邮件发送失败,subject="+mailInfo.getSubject(),ex.getMessage());
  }
  return false;
 }
 
 public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
  // 判断是否需要身份认证
  MyAuthenticator authenticator = null;
  Properties pro = mailInfo.getProperties();
  // 如果需要身份认证，则创建一个密码验证器
  if (mailInfo.isValidate()) {
   authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
  }
  // 根据邮件会话属性和密码验证器构造一个发送邮件的session
  Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
  try {
   // 根据session创建一个邮件消息
   Message mailMessage = new MimeMessage(sendMailSession);
   // 创建邮件发送者地址
   Address from = new InternetAddress(mailInfo.getFromAddress());
   // 设置邮件消息的发送者
   mailMessage.setFrom(from);
   // 创建邮件的接收者地址，并设置到邮件消息中（单发）
  // Address to = new InternetAddress(mailInfo.getToAddress());
   
   // 创建邮件的接收者地址，并设置到邮件消息中（群发）   
   String[] mailToAddress = mailInfo.getToAddress() ;

   int len = mailToAddress.length ;

   Address to[] = new InternetAddress[len] ;

   for(int i=0;i<len;i++){

   to[i] = new InternetAddress(mailToAddress[i]) ;

   }
   //群发
   mailMessage.setRecipients(Message.RecipientType.TO,to);
   // Message.RecipientType.TO属性表示接收者的类型为TO
   //单发
   // mailMessage.setRecipient(Message.RecipientType.TO, to);
   // 设置邮件消息的主题
   mailMessage.setSubject(mailInfo.getSubject());
   // 设置邮件消息发送的时间
   mailMessage.setSentDate(new Date());
   // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
   Multipart mainPart = new MimeMultipart();
   // 创建一个包含HTML内容的MimeBodyPart
   BodyPart html = new MimeBodyPart();
   // 设置HTML内容
   html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
   mainPart.addBodyPart(html);
   // 将MiniMultipart对象设置为邮件内容
   mailMessage.setContent(mainPart);
   // 发送邮件
   Transport.send(mailMessage);
   return true;
  } catch (MessagingException ex) {
     logger.warn("邮件发送失败,subject="+mailInfo.getSubject(),ex);
  }
  return false;
 }
}