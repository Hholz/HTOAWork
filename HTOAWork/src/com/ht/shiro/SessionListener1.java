package com.ht.shiro;

import org.apache.shiro.session.Session;  
import org.apache.shiro.session.SessionListener;  
  
public class SessionListener1 implements SessionListener {  
  
    @Override  
    public void onStart(Session session) {//�Ự�������� �ѽ���shiro�Ĺ������ʹ����������  
        // TODO Auto-generated method stub  
        System.out.println("�Ự������" + session.getId());  
    }  
  
    @Override  
    public void onStop(Session session) {//�˳�  
        // TODO Auto-generated method stub  
        System.out.println("�˳��Ự��" + session.getId());  
    }  
  
    @Override  
    public void onExpiration(Session session) {//�Ự����ʱ����  
        // TODO Auto-generated method stub  
        System.out.println("�Ự���ڣ�" + session.getId());   
    }  
  
}  