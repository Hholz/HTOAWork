package com.ht.annotation;

import java.lang.annotation.*;    

/**  
 *�Զ���ע�� ����service  
 */    
    
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface SystemServiceLog {    
    
    String description()  default "";    
    
    
}  