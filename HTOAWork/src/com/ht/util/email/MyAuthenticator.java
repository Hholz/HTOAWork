package com.ht.util.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * �����û�����������֤��
 * Created by ziyi on 15-5-25. 
 */
public class MyAuthenticator extends Authenticator {
 String userName = null;
 String password = null;
 public MyAuthenticator() {
 }
 public MyAuthenticator(String username, String password) {
  this.userName = username;
  this.password = password;
 }
 protected PasswordAuthentication getPasswordAuthentication() {
  return new PasswordAuthentication(userName, password);
 }
}